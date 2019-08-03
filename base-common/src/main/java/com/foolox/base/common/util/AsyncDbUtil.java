package com.foolox.base.common.util;

import com.foolox.base.common.disruptor.event.DbEventType;
import com.foolox.base.common.disruptor.event.OperationEvent;
import com.foolox.base.constant.disruptor.DbEvent;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.data.repository.CrudRepository;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public class AsyncDbUtil {

    /**
     * 异步保存DbEvent 到数据库
     *
     * @param event
     * @param crudRepository
     */
    public static void published(DbEvent event, CrudRepository crudRepository) {
        published(event, crudRepository, DbEventType.SAVE);
    }

    /**
     * 异步操作UserEvent数据库
     *
     * @param event
     * @param crudRepository
     * @param dbEventType
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void published(DbEvent event, CrudRepository crudRepository, DbEventType dbEventType) {
        Disruptor<OperationEvent> disruptor = (Disruptor<OperationEvent>) SpringContextUtil.getApplicationContext().getBean("disruptor");
        long seq = disruptor.getRingBuffer().next();
        OperationEvent operationEvent = disruptor.getRingBuffer().get(seq);
        operationEvent.setDbEvent(event);
        operationEvent.setRepository(crudRepository);
        operationEvent.setDbEventType(dbEventType);
        disruptor.getRingBuffer().publish(seq);
    }
}
