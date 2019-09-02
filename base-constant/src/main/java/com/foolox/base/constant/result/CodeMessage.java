package com.foolox.base.constant.result;

import lombok.Getter;
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
    //通用异常 500100
    public static CodeMessage SUCCESS = new CodeMessage(0, "sucess");
    public static CodeMessage SERVER_ERROR = new CodeMessage(500100, "服务端异常");
    public static CodeMessage PARAMS_EMPTY_ERROR = new CodeMessage(500101, "参数为空异常：%s");
    public static CodeMessage GAME_SERVER_UNAVAILABLE_ERROR = new CodeMessage(500102, "没有可用的游戏服务器");
    public static CodeMessage VALIDATE_ERROR = new CodeMessage(500103,"参数错误：%s");
    public static CodeMessage REPEAT_REQUEST= new CodeMessage(500104,"重复请求:%s");
    public static CodeMessage MACHINEID_INCORRECT = new CodeMessage(500105, "错误的游戏服务器");
    public static CodeMessage RUNTIME_ERROR = new CodeMessage(500106, "请求异常:%s");

    //登录模块
    public static CodeMessage ILLEGAL_TOKEN_ERROR = new CodeMessage(500201, "TOKEN状态异常：%s");
    public static CodeMessage HAS_NO_TOKEN_ERROR = new CodeMessage(500202, "用户尚未登录：%s");
    public static CodeMessage LOGIN_TOKEN_EXPIRED = new CodeMessage(500203,"Token已过期");
    public static CodeMessage LOGIN_TOKEN_INCORRECT = new CodeMessage(500204,"无效Token");
    public static CodeMessage LOGIN_USER_NOT_EXIST = new CodeMessage(500205,"用户不存在");
    public static CodeMessage LOGIN_PASSWORD_INCORRECT = new CodeMessage(500206,"用户名或密码错误");
    public static CodeMessage LOGIN_STATE_INCORRECT = new CodeMessage(500207,"用户被冻结");
    public static CodeMessage ILLEGAL_STATUS_ERROR = new CodeMessage(500208,"用户状态异常：%s");

    //货币相关
    public static CodeMessage COST_OUT_ERROR = new CodeMessage(500301, "创建房间费用不足");


    //房间模块
    public static CodeMessage ILLEGAL_ROOMNO = new CodeMessage(500401, "房间号错误");
    public static CodeMessage ROOM_ON_DISSOLVE = new CodeMessage(500402, "房间正在解散中");
    public static CodeMessage ROOM_IS_FULL = new CodeMessage(500403, "房间已满员");

    //俱乐部模块
    public static CodeMessage PLAYER_NOT_IN_CLUB = new CodeMessage(500501, "玩家不在俱乐部中");



    private int code;
    private String message;

    private CodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeMessage fillArgs(Object... args) {
        this.message = String.format(this.message, args);
        return this;
    }
}
