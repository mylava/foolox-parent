package com.foolox.base.io.config;

import com.foolox.base.common.util.MessageKey;
import com.foolox.base.constant.annotation.MessageMapping;
import com.foolox.base.constant.annotation.MessageMeta;
import com.foolox.base.io.dispatch.CmdExecutor;
import com.foolox.base.io.dispatch.MessageDispatcher;
import com.foolox.base.io.input.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

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
            if (null != clz.getAnnotation(Controller.class)) {
                Method[] methods = clz.getDeclaredMethods();
                for (Method method : methods) {
                    //处理方法上的注解，类比Spring mvc 中的 requestMapping
                    MessageMapping mapperAnnotation = method.getAnnotation(MessageMapping.class);
                    if (mapperAnnotation != null) {
                        short[] meta = getMessageMeta(method);
                        if (meta == null) {
                            throw new RuntimeException(
                                    String.format("facade[%s] method[%s] lack of RequestMapping annotation",
                                            clz.getName(), method.getName()));
                        }

                        short module = meta[0];
                        short cmd = meta[1];

                        String key = MessageKey.buildKey(module, cmd);

                        CmdExecutor cmdExecutor = CmdExecutor.valueOf(method, method.getParameterTypes(), bean);
                        messageDispatcher.registerMethodInvoke(key, cmdExecutor);
                        log.info("-------regist Controller {} -------", bean);
                    }
                }
            }
        } catch (Exception e) {
            log.error("dispatcher init error：", e);
        }
        return bean;
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
                    short[] meta = {protocol.module(), protocol.cmd()};
                    return meta;
                }
            }
        }
        return null;
    }

}
