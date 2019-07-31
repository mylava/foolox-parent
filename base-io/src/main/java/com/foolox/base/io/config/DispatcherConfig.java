package com.foolox.base.io.config;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.io.dispatch.CmdExecutor;
import com.foolox.base.io.dispatch.MessageDispatcher;
import com.foolox.base.io.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
@Component
public class DispatcherConfig implements BeanPostProcessor, Ordered {
    @Resource
    private MessageDispatcher messageDispatcher;

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class<?> clz = bean.getClass();
            Facade facade = clz.getAnnotation(Facade.class);
            if (null != facade && MessageHandler.class.isAssignableFrom(clz)) {
                //设置responseCommand
                ((MessageHandler)bean).setCommand(facade.value().toString());
                //注册到分发器
                Method method = clz.getMethod("execute", String.class, JSONObject.class);
                CmdExecutor cmdExecutor = CmdExecutor.valueOf(method, method.getParameterTypes(), bean);
                messageDispatcher.registerMethodInvoke(facade.value().toString(), cmdExecutor);
                log.info("-------regist facade {} -------", bean);
            }
        } catch (Exception e) {
            log.error("dispatcher init error：", e);
        }
        return bean;
    }
}
