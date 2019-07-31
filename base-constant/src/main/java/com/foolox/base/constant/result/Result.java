package com.foolox.base.constant.result;

import lombok.Data;

/**
 * comment: http统一返回结果
 *
 * @author: lipengfei
 * @date: 11/05/2019
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.message = "sucess";
        this.data = data;
    }

    public Result(CodeMessage codeMessage) {
        if (null == codeMessage) {
            return;
        }
        this.code = codeMessage.getCode();
        this.message = codeMessage.getMessage();
    }

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(CodeMessage codeMessage) {
        return new Result<T>(codeMessage);
    }

}
