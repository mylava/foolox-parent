package com.foolox.base.constant.result;

import lombok.Data;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 27/07/2019
 */
@Data
public class CommonMessage<T> {
    private String command;
    private int code;
    private String message;
    private T data;

    public CommonMessage(String command, T data) {
        this.command = command;
        this.code = 0;
        this.message = "sucess";
        this.data = data;
    }

    public CommonMessage(String command, CodeMessage codeMessage) {
        this.command = command;
        if (null == codeMessage) {
            return;
        }
        this.code = codeMessage.getCode();
        this.message = codeMessage.getMessage();
    }

    public CommonMessage(String command, int code, String message) {
        this.command = command;
        this.code = code;
        this.message = message;
    }
}
