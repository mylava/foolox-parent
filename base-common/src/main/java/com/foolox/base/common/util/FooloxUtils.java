package com.foolox.base.common.util;


import com.foolox.base.constant.rediskey.PlayerPrefix;
import com.foolox.base.db.domain.ClientSession;

import java.lang.management.ManagementFactory;
import java.util.UUID;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 12/05/2019
 */
public class FooloxUtils {

    private static MD5 md5 = new MD5();

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String md5(String str) {
        return md5.getMD5ofStr(md5.getMD5ofStr(str));
    }

    public static final String IP = IpAddrUtil.getInnetIp();
    // IP + 当前进程id
    public static final String MACHINE_ID = IP + ":" + ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

    /**
     * 通过 playerId 获取 ClientSession
     *
     * @param playerId
     * @return
     */
    public static ClientSession getClientSessionById(Long playerId) {
        return RedisUtil.get(PlayerPrefix.PLAYERID_SESSION, playerId.toString(), ClientSession.class);
    }

    /**
     * 保存 playerId 与 ClientSession 映射关系到缓存
     *
     * @param playerId
     */
    public static void setClientSessionById(Long playerId, ClientSession clientSession) {
        RedisUtil.set(PlayerPrefix.PLAYERID_SESSION, playerId.toString(), clientSession);
    }

}
