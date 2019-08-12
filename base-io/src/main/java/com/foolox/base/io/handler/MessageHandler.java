package com.foolox.base.io.handler;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.CommonMessage;

/**
 * comment: 定义Facade需要实现的方法，同时完成一些前置设置
 * 所有的Facade需要继承此类
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
public abstract class MessageHandler {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public <T> CommonMessage<T> success(T data) {
        return new CommonMessage<T>(this.getCommand(), data);
    }

    public <T> CommonMessage<T> fail(CodeMessage codeMessage) {
        return new CommonMessage<T>(this.getCommand(), codeMessage);
    }

    public <T> CommonMessage<T> fail(int code, String message) {
        return new CommonMessage<T>(this.getCommand(), code, message);
    }

    public abstract void execute(Long playerId, JSONObject message);
}
