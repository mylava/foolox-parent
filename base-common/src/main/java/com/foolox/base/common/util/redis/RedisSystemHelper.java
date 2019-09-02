package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.rediskey.SystemPrefix;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public class RedisSystemHelper {
    /**
     * roomNo是否有对应的machine
     * @param roomNo
     * @return
     */
    public static Boolean existRoomNo(String roomNo) {
        return RedisUtil.exists(SystemPrefix.ROOMNO_MACHINEID, roomNo);
    }

    /**
     * 保存 roomNo 所在的服务器
     * @param roomNo
     * @param machineId
     * @return
     */
    public static void saveRoomNo(String roomNo, String machineId) {
        RedisUtil.set(SystemPrefix.ROOMNO_MACHINEID, roomNo, machineId);
    }

    /**
     * 保存系统字典表中的数据到缓存
     * @param code
     * @param value
     * @return
     */
    public static void setSysCodeValue(String code, String value) {
        RedisUtil.set(SystemPrefix.CODE_VALUE, code, value);
    }

    /**
     * 获取系统字典表中的数据
     * @param code
     * @return
     */
    public static String getSysCodeValue(String code) {
        return RedisUtil.get(SystemPrefix.CODE_VALUE, code);
    }
}
