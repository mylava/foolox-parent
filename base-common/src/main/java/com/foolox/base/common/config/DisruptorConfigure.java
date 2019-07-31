package com.foolox.base.common.config;

import com.foolox.base.common.disruptor.event.OperationEvent;
import com.foolox.base.common.disruptor.handler.DisruptorExceptionHandler;
import com.foolox.base.common.disruptor.handler.OperationEventFactory;
import com.foolox.base.common.disruptor.handler.OperationEventHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * comment: 并发框架disruptor
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
@Configuration
public class DisruptorConfigure {

    @Bean(name = "disruptor")
    public Disruptor<OperationEvent> disruptor() {
        //创建线程池
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger index = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(null, runnable, "disruptor-thread-" + index.getAndIncrement());
            }
        };
        //创建事件工厂
        OperationEventFactory factory = new OperationEventFactory();
        //创建Disruptor
        Disruptor<OperationEvent> disruptor = new Disruptor<OperationEvent>(factory, 1024, threadFactory, ProducerType.SINGLE, new SleepingWaitStrategy());
        //添加异常处理器
        disruptor.setDefaultExceptionHandler(new DisruptorExceptionHandler());
        //添加事件处理器
        disruptor.handleEventsWith(new OperationEventHandler());
        //启动
        disruptor.start();
        return disruptor;
    }
}
