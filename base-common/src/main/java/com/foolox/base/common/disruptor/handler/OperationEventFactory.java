package com.foolox.base.common.disruptor.handler;

import com.foolox.base.common.disruptor.event.OperationEvent;
import com.lmax.disruptor.EventFactory;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
public class OperationEventFactory implements EventFactory<OperationEvent> {
    @Override
    public OperationEvent newInstance() {
        return new OperationEvent();
    }
}
