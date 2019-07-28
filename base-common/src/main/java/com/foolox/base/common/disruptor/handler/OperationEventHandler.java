package com.foolox.base.common.disruptor.handler;

import com.foolox.base.common.disruptor.event.DbEventType;
import com.foolox.base.common.disruptor.event.OperationEvent;
import com.lmax.disruptor.EventHandler;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
public class OperationEventHandler implements EventHandler<OperationEvent> {

    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(OperationEvent operationEvent, long sequence, boolean endOfBatch) throws Exception {
        if(DbEventType.SAVE == operationEvent.getDbEventType()){
            if(operationEvent.getRepository()!=null){
                operationEvent.getRepository().save(operationEvent.getDbEvent()) ;
            }
        }else if(DbEventType.DELETE == operationEvent.getDbEventType()){
            if(operationEvent.getRepository()!=null){
                operationEvent.getRepository().delete(operationEvent.getDbEvent()) ;
            }
        }
    }
}
