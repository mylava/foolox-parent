package com.foolox.base.io.dispatch;

import com.foolox.base.common.util.MessageKey;
import com.foolox.base.io.input.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * comment: 消息分发器
 *
 * @author: lipengfei
 * @date: 16/07/2019
 */
@Slf4j
@Component
public class MessageDispatcher {
    /**
     * [module_cmd, CmdExecutor]
     */
    private static final Map<String, CmdExecutor> MODULE_CMD_HANDLERS = new HashMap<>();

    public void registerMethodInvoke(String key, CmdExecutor executor) {
        if (MODULE_CMD_HANDLERS.containsKey(key)) {
            throw new RuntimeException(String.format("module[%d] duplicated", key));
        }
        MODULE_CMD_HANDLERS.put(key, executor);
    }

    /**
     * message entrance, in which io thread dispatch messages
     *
     * @param userId
     * @param message
     */
    public void dispatch(String userId, Message message) throws InvocationTargetException, IllegalAccessException {
        short module = message.getModule();
        short cmd = message.getCmd();

        CmdExecutor cmdExecutor = MODULE_CMD_HANDLERS.get(MessageKey.buildKey(module, cmd));
        if (cmdExecutor == null) {
            log.error("message executor missed, module={},cmd={}", module, cmd);
            return;
        }

        Object[] params = convertToMethodParams(userId, cmdExecutor.getParams(), message);
        Object controller = cmdExecutor.getHandler();
        cmdExecutor.getMethod().invoke(controller, params);
    }

    /**
     * 将各种参数转为被RequestMapper注解的方法的实参
     *
     * @param userId
     * @param methodParams
     * @param message
     * @return
     */
    private Object[] convertToMethodParams(String userId, Class<?>[] methodParams, Message message) {
        Object[] result = new Object[methodParams == null ? 0 : methodParams.length];

        for (int i = 0; i < result.length; i++) {
            Class<?> param = methodParams[i];
            if (String.class.isAssignableFrom(param)) {
                result[i] = userId;
            } else if (Message.class.isAssignableFrom(param)) {
                result[i] = message;
            }
        }
        return result;
    }

}
