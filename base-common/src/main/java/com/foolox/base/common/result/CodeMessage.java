package com.foolox.base.common.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 11/05/2019
 */
@Getter
@ToString
public class CodeMessage {
    private int code;
    private String message;

    //通用异常 500100
    public static CodeMessage SUCCESS = new CodeMessage(0,"sucess");
    public static CodeMessage SERVER_ERROR = new CodeMessage(500100,"服务端异常");
    public static CodeMessage VALIDATE_ERROR = new CodeMessage(500101,"参数校验异常：%s");
    public static CodeMessage PARAMS_EMPTY_ERROR = new CodeMessage(500102,"参数为空异常：%s");
    public static CodeMessage ILLEGAL_TOKEN_ERROR = new CodeMessage(500103,"TOKEN状态异常：%s");
    public static CodeMessage HAS_NO_TOKEN_ERROR = new CodeMessage(500103,"用户尚未登录：%s");

    private CodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeMessage fillArgs(Object... args) {
        this.message = String.format(this.message,args);
        return this;
    }
}
