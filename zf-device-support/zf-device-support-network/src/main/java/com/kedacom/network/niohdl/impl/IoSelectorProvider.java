package com.kedacom.network.niohdl.impl;

import com.kedacom.network.niohdl.core.IoProvider;
import com.kedacom.network.utils.CloseUtil;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class IoSelectorProvider implements IoProvider {

    private final Selector readDataFromChannelSelector;
    private final Selector writeDataToChannelSelector;

    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    // 是否处于输入通道的注册过程
    private final AtomicBoolean readLocker = new AtomicBoolean(false);
    // 是否处于输出通道的注册过程
    private final AtomicBoolean writeLocker = new AtomicBoolean(false);

    private final HashMap<SelectionKey, Runnable> handlerMap = new HashMap<>();

    private final ExecutorService readDataFromChannelHanlerPool;
    private final ExecutorService writeDataToChannelHandlerPool;

    public IoSelectorProvider() throws IOException {
        this.readDataFromChannelSelector = Selector.open();
        this.writeDataToChannelSelector = Selector.open();

        //建立线程池
        readDataFromChannelHanlerPool = Executors.newFixedThreadPool(2,
                new IoProviderThreadFactory("IoProvider-Input-Thread-"));
        writeDataToChannelHandlerPool = Executors.newFixedThreadPool(2,
                new IoProviderThreadFactory("IoProvider-Output-Thread-"));

        //建立两个线程，执行输入和输出的select
        startReadDataFromChannel();
        startWriteDataToChannel();

    }

    private void startReadDataFromChannel() {
        Thread thread = new Thread("ioProvider ReadSelector Thread") {
            //private Boolean done = false;
            @Override
            public void run() {
                AtomicBoolean locker = readLocker;
                try {
                    while (!isClosed.get()) {
                        if (readDataFromChannelSelector.select() == 0) {
                            //这里有一个等待操作，等待注册结束
                            waitSelection(readLocker);
                            continue;
                        } else if (locker.get()) {
                            waitSelection(readLocker);
                        }

                        Iterator<SelectionKey> iterator = readDataFromChannelSelector.selectedKeys().iterator();
                        //System.out.println("selectedKeys数量："+readSelector.selectedKeys().size());

                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();//此处格外重要
                            if (key.isValid()) {
                                // 取消继续对keyOps的监听
                                key.interestOps(key.readyOps() & ~SelectionKey.OP_READ);

                                //线程池执行read操作
                                readDataFromChannelHanlerPool.execute(handlerMap.get(key));
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    private void startWriteDataToChannel() {
        Thread thread = new Thread("Clink IoSelectorProvider WriteSelector Thread") {
            @Override
            public void run() {
                while (!isClosed.get()) {
                    AtomicBoolean locker = writeLocker;
                    try {
                        //TODO 加上不为0的处理，locker.get()==true 直接要处理完
                        if (writeDataToChannelSelector.select() == 0) {
                            waitSelection(writeLocker);
                            continue;
                        } else if (locker.get()) {
                            waitSelection(writeLocker);
                        }

                        Set<SelectionKey> selectionKeys = writeDataToChannelSelector.selectedKeys();
                        //TODO 改为迭代器
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()){
                            SelectionKey selectionKey = iterator.next();
                            if (selectionKey.isValid()) {
                                selectionKey.interestOps(selectionKey.readyOps() & ~SelectionKey.OP_WRITE);

                                writeDataToChannelHandlerPool.execute(handlerMap.get(selectionKey));
                            }
                        }
                        selectionKeys.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    public void close() {
        //compareAndSet方法：当布尔值为expect，则将其换成update，成功返回true
        if (isClosed.compareAndSet(false, true)) {
            readDataFromChannelHanlerPool.shutdown();
            writeDataToChannelHandlerPool.shutdown();

            handlerMap.clear();

            readDataFromChannelSelector.wakeup();
            writeDataToChannelSelector.wakeup();
            CloseUtil.close(readDataFromChannelSelector, writeDataToChannelSelector);
        }
    }

    @Override
    public boolean registerInput(SocketChannel channel, InputHandler inputHandler) {

        return register(channel, readDataFromChannelSelector, readLocker, inputHandler, handlerMap, SelectionKey.OP_READ) != null;
    }

    @Override
    public boolean registerOutput(SocketChannel channel, OutputHandler outputHandler) {

        return register(channel, writeDataToChannelSelector, writeLocker, outputHandler, handlerMap, SelectionKey.OP_WRITE) != null;
    }

    @Override
    public void unRegisterInput(SocketChannel channel) {
        unRegister(channel, readDataFromChannelSelector, handlerMap);
    }

    @Override
    public void unRegisterOutput(SocketChannel channel) {
        unRegister(channel, writeDataToChannelSelector, handlerMap);
    }

    private static void waitSelection(final AtomicBoolean locker) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (locker) {
            if (locker.get()) {
                try {
                    System.out.println("flag");
                    //暂停当前线程，直到被唤醒
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static SelectionKey register(SocketChannel channel, Selector selector, AtomicBoolean locker,
                                         Runnable ioCallback, HashMap<SelectionKey, Runnable> map,
                                         int ops) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (locker) {
            locker.set(true);
            try {
                //这时候select会立即返回，若是0，则进入waitSelection，然后进行locker.wait
                selector.wakeup();

                SelectionKey key = null;
                if (channel.isRegistered()) {
                    key = channel.keyFor(selector);
                    if (key != null) {
                        key.interestOps(key.readyOps() | ops);
                    }
                }
                //（注册write）如果已注册过read，还没注册write，此时key为null
                if (key == null) {
                    //TODO
                    key = channel.register(selector, ops);
                    map.put(key, ioCallback);
                }
                return key;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                //System.out.println("注册成功！");
                locker.set(false);
                //唤醒locker.wait()
                locker.notify();
            }
        }
    }

    private static void unRegister(SocketChannel channel, Selector selector, HashMap<SelectionKey, Runnable> map) {
        if (channel.isRegistered()) {
            SelectionKey key = channel.keyFor(selector);
            if (key != null) {
                key.cancel();
                map.remove(key);
            }
            selector.wakeup();
        }
    }


    static class IoProviderThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        IoProviderThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            this.group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
