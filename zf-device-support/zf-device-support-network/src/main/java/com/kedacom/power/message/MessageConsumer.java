package com.kedacom.power.message;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Slf4j
public class MessageConsumer implements Runnable {

    private BlockingQueue<RequestParam> queue;

    private MessageSender messageSender;

    public MessageConsumer(BlockingQueue<RequestParam> queue, MessageSender messageSender) {
        this.queue = queue;
        this.messageSender = messageSender;
    }

    @Override
    public void run() {
        while (MessageSender.consumerStatus) {
            try {
                messageSender.consumerMessage(queue.take());
            } catch (Exception e) {
                log.error("消息列队异常", e);
            }
        }
    }
}
