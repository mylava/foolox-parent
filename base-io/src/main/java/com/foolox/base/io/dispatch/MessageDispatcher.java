package com.foolox.base.io.dispatch;

import com.alibaba.fastjson.JSONObject;
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

    private static final Map<String, CmdExecutor> COMMAND_HANDLERS = new HashMap<>();

    public void registerMethodInvoke(String key, CmdExecutor executor) {
        if (COMMAND_HANDLERS.containsKey(key)) {
            throw new RuntimeException(String.format("handler [%s] duplicated", key));
        }
        COMMAND_HANDLERS.put(key, executor);
    }

    /**
     * message entrance, in which io thread dispatch messages
     *
     * @param playerId
     * @param command
     * @param message
     */
    public void dispatch(Long playerId, String command, JSONObject message) throws InvocationTargetException, IllegalAccessException {
        CmdExecutor cmdExecutor = COMMAND_HANDLERS.get(command.toLowerCase());
        if (cmdExecutor == null) {
            log.error("message executor missed, command={}", command);
            return;
        }
        Object[] params = convertToMethodParams(playerId, cmdExecutor.getParams(), message);
        Object handler = cmdExecutor.getHandler();
        cmdExecutor.getMethod().invoke(handler, params);
    }

    /**
     * 将各种参数转为被RequestMapper注解的方法的实参
     *
     * @param playerId
     * @param methodParams
     * @param message
     * @return
     */
    private Object[] convertToMethodParams(Long playerId, Class<?>[] methodParams, JSONObject message) {
        Object[] result = new Object[methodParams == null ? 0 : methodParams.length];

        for (int i = 0; i < result.length; i++) {
            Class<?> param = methodParams[i];
            if (Long.class.isAssignableFrom(param)) {
                result[i] = playerId;
            } else if (JSONObject.class.isAssignableFrom(param)) {
                result[i] = message;
            }
        }
        return result;
    }

}
