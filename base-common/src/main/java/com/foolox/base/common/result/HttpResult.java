package com.foolox.base.common.result;

import lombok.Data;

/**
 * comment: http统一返回结果
 *
 * @author: lipengfei
 * @date: 11/05/2019
 */
@Data
public class HttpResult<T> {
    private int code;
    private String message;
    private T data;

    private HttpResult(T data) {
        this.code = 0;
        this.message = "sucess";
        this.data = data;
    }

    public HttpResult(CodeMessage codeMessage) {
        if (null == codeMessage) {
            return;
        }
        this.code = codeMessage.getCode();
        this.message = codeMessage.getMessage();
    }

    /**
     * 成功
     * @param <T>
     * @return
     */
    public static <T> HttpResult<T> success(T data) {
        return  new HttpResult<T>(data);
    }

    /**
     * 失败
     * @param <T>
     * @return
     */
    public static <T> HttpResult<T> fail(CodeMessage codeMessage) {
        return new HttpResult<T>(codeMessage);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
