package com.foolox.base.io.config;

import com.foolox.base.io.dispatch.MessageDispatcher;
import com.foolox.base.io.server.IOEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
@Configuration
public class IOEventHandlerConfig {
    @Resource
    private MessageDispatcher messageDispatcher;

    @Bean
    public IOEventHandler ioEventHandler() {
        final IOEventHandler handler = new IOEventHandler();
        handler.setMessageDispatcher(messageDispatcher);
        return handler;
    }
}
