package com.foolox.base.common.disruptor.event;

import com.foolox.base.constant.disruptor.DbEvent;
import lombok.Data;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * comment: 数据库操作事件
 *
 * @author: lipengfei
 * @date: 27/07/2019
 */
@Data
public class OperationEvent {
    private long id;
    //事件
    private DbEvent dbEvent;
    //命令
    private DbEventType dbEventType;
    //dao
    private MongoRepository repository;
}
