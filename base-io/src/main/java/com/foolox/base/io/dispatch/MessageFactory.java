package com.foolox.base.io.dispatch;

import com.foolox.base.common.util.MessageKey;
import com.foolox.base.constant.annotation.MessageMeta;
import com.foolox.base.io.input.Message;
import com.foolox.base.io.util.ClassScanner;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * comment: 消息工厂类，使用自定有类扫描、注册
 *
 * @author: lipengfei
 * @date: 15/07/2019
 */
@Slf4j
public class MessageFactory {
    private static MessageFactory instance = new MessageFactory();

    private Map<String, Class<? extends Message>> id2Clazz = new HashMap<>();

    public static MessageFactory getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public void init(String packageName) {
        Set<Class<?>> messages = ClassScanner.listAllSubclasses(packageName, Message.class);
        for (Class<?> clazz : messages) {
            MessageMeta meta = clazz.getAnnotation(MessageMeta.class);
            if (meta == null) {
                throw new RuntimeException("messages[" + clazz.getSimpleName() + "] missed MessageMeta annotation");
            }
            log.info("init messageHandler");
            String key = MessageKey.buildKey(meta.module(), meta.cmd());
            if (id2Clazz.containsKey(key)) {
                throw new RuntimeException("message meta [" + key + "] duplicate！！");
            }
            id2Clazz.put(key, (Class<? extends Message>) clazz);
        }
    }

    /**
     * 返回消息的模板class
     *
     * @param module
     * @param cmd
     * @return
     */
    public Class<? extends Message> getMessageMeta(short module, short cmd) {
        String key = MessageKey.buildKey(module, cmd);
        return getMessageMeta(key);
    }

    public Class<? extends Message> getMessageMeta(String key) {
        return id2Clazz.get(key);
    }
}
