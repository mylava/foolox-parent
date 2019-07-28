package com.foolox.base.io.config;

import com.foolox.base.constant.annotation.MessageMeta;
import com.foolox.base.io.dispatch.MessageDispatcher;
import com.foolox.base.io.dispatch.MessageFactory;
import com.foolox.base.io.input.Message;
import com.foolox.base.io.server.IOEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
@Configuration
public class IOEventHandlerConfig {

    @Value("${foolox.message.packageName}")
    private String messagePackage;
    @Resource
    private MessageDispatcher messageDispatcher;

    @Bean
    public IOEventHandler ioEventHandler() {
        final IOEventHandler handler = new IOEventHandler();
        dispatcherInit();
        handler.setMessageDispatcher(messageDispatcher);
        return handler;
    }

    public void dispatcherInit() {
        //初始化Message
        MessageFactory.getInstance().init(messagePackage);
        //初始化Facade,代码重构后，交由 DispatcherConfig 初始化
    }


    /**
     * 返回方法所带Message参数的元信息
     *
     * @param method
     * @return
     */
    private short[] getMessageMeta(Method method) {
        for (Class<?> paramClazz : method.getParameterTypes()) {
            if (Message.class.isAssignableFrom(paramClazz)) {
                MessageMeta protocol = paramClazz.getAnnotation(MessageMeta.class);
                if (protocol != null) {
                    short[] meta = { protocol.module(), protocol.cmd() };
                    return meta;
                }
            }
        }
        return null;
    }


}
