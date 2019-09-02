package com.foolox.base.constant.rediskey;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 16/05/2019
 */
public class SystemPrefix extends BasePrefix {
    //系统字典
    public static final SystemPrefix CONFIG = new SystemPrefix("config");
    public static final SystemPrefix GAMES = new SystemPrefix("games");
    /**
     * --------------- ---------------
     * 上边需要重新上设计
     * --------------- ---------------
     */
    public static final SystemPrefix ROOMNO_MACHINEID = new SystemPrefix("system:machine:roomNo:machineId");

    public static final SystemPrefix CODE_VALUE = new SystemPrefix("system:sysdic:code:value");

    public static final SystemPrefix CONFIG_ID_PLAYWAY = new SystemPrefix("config:playwayId");
    // gamemodel.code -- sysdic
    public static final SystemPrefix CONFIG_CODE_SYSDIC = new SystemPrefix("config:sysdic");
    // aiconfig
    public static final SystemPrefix CONFIG_AI = new SystemPrefix("config:ai");
    private SystemPrefix(String prefix) {
        super(prefix);
    }


}
