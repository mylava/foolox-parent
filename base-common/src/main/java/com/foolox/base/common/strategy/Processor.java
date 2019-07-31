package com.foolox.base.common.strategy;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.CommonMessage;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
public abstract class Processor {
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

    public abstract void process(String userId, JSONObject param);
}
