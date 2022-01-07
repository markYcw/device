//package com.kedacom.device.core.notify.mt;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author wangxy
// * @describe
// * @date 2021/12/3
// */
//@Slf4j
//@Component
//public class MtNotifyFactory {
//
//    @Resource
//    MtSeizeNotify mtSeizeNotify;
//
//    @Resource
//    MtDropLineNotify mtDropLineNotify;
//
//    private static final Integer SEIZE = 100;
//
//    private static final Integer DROP_LINE = 1;
//
////    public static void init() {
////
////        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
////
////        factory.register(DeviceType.MT.getValue(), 1, new MtDropLineNotify());
////
////        factory.register(DeviceType.MT.getValue(), 100, new MtSeizeNotify());
////
////        factory.register(DeviceType.MT5.getValue(), 1, new MtDropLineNotify());
////
////        factory.register(DeviceType.MT5.getValue(), 100, new MtSeizeNotify());
////
////    }
//
//    public void handleMtNotify(Integer mtId, Integer msgType, String content) {
//
//        // 终端的掉线通知
//        if (SEIZE.equals(msgType)) {
//            log.info("mtId 终端掉线");
//            mtDropLineNotify.consumeMessage(mtId);
//        }
//        // 终端的抢占通知
//        if (DROP_LINE.equals(msgType)) {
//
//            log.info("mtId 终端被抢占");
//            mtSeizeNotify.consumeMessage(mtId, content);
//        }
//
//    }
//
//}
