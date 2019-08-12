package com.foolox.base.constant.rediskey;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 23/08/2018
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
public class PlayerPrefix extends BasePrefix {

    //key命名规范： key_value

    //PLAYERID TOKEN
    public static final PlayerPrefix PLAYERID_TOKEN = new PlayerPrefix("player:playerid:token");
    //PLAYERID FIELD
    public static final PlayerPrefix PLAYERID_FIELD = new PlayerPrefix("player:playerid:field");
    //getGamePlayerCacheBean:
    //进入房间的用户与ClientSession对应关系  key(playerid -- clientSession)
    //getApiUserCacheBean 保存 playerid 与 clientsession 的映射关系
    public static final PlayerPrefix PLAYERID_SESSION = new PlayerPrefix("player:playerid:session");
    public static final PlayerPrefix PLAYERID_ROOMNO = new PlayerPrefix("player:playerid:roomno");
    public static final PlayerPrefix PLAYER_CLIENTSESSIONID_PLAYER = new PlayerPrefix("player:clientsessionid:gameplayer");
    //getApiUserCacheBean
    public static final PlayerPrefix PLAYER_LOGIN_CLIENTSESSION = new PlayerPrefix("player:login:clientsession");
    //默认token过期时间为2天
    private static final int TOKEN_EXPIRE = 3600 * 24 * 2;
    private PlayerPrefix(String prefix) {
        super(TOKEN_EXPIRE, prefix);
    }
}
