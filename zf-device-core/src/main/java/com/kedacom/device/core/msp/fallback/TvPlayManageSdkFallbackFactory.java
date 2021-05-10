package com.kedacom.device.core.msp.fallback;

import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.avIntegration.response.tvplay.*;
import com.kedacom.device.core.msp.TvPlayManageSdk;
import com.kedacom.device.core.msp.exception.MspRemoteCallException;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 10:08
 */
public class TvPlayManageSdkFallbackFactory implements FallbackFactory<TvPlayManageSdk> {

    private volatile TvPlayManageSdk tvPlayManageSdk;

    @Override
    public TvPlayManageSdk create(Throwable cause) {

        if (this.tvPlayManageSdk == null) {

            synchronized (TvPlayManageSdkFallbackFactory.class) {

                if (this.tvPlayManageSdk == null) {

                    this.tvPlayManageSdk = new TvPlayManageSdk() {
                        @Override
                        public BatchStartResponse batchStart(BatchStartRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public BatchStopResponse batchStop(BatchStopRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvPlayClearResponse clear(TvPlayClearRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvPlayStyleResponse style(TvPlayStyleRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvPlayOpenResponse open(TvPlayOpenRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvPlayOrderResponse order(TvPlayOrderRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }

                        @Override
                        public TvPlayActionResponse action(TvPlayActionRequest request) {
                            throw new MspRemoteCallException(cause.getMessage());
                        }
                    };

                }

            }

        }

        return this.tvPlayManageSdk;
    }
}