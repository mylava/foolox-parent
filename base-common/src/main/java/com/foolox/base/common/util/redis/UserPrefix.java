package com.foolox.base.common.util.redis;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 23/08/2018
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
public class UserPrefix extends BasePrefix{

    //默认token过期时间为2天
    private static final int TOKEN_EXPIRE = 3600*24*2;

    private UserPrefix(String prefix) {
        super(TOKEN_EXPIRE,prefix);
    }

    public static final UserPrefix TOKEN = new UserPrefix("user:token");

    //getGamePlayerCacheBean:
    //进入房间的用户与ClientSession对应关系  key(userId -- clientSession)
    //getApiUserCacheBean 保存 userId 与 clientsession 的映射关系
    public static final UserPrefix PLAYER_ID_CLIENTSESSION = new UserPrefix("player:userid:clientsession");
    public static final UserPrefix PLAYER_ROOM = new UserPrefix("clientSessionList:room");
    public static final UserPrefix PLAYER_CLIENTSESSIONID_PLAYER = new UserPrefix("player:clientsessionid:gameplayer");
    //getApiUserCacheBean
    public static final UserPrefix PLAYER_LOGIN_CLIENTSESSION = new UserPrefix("player:login:clientsession");
}
