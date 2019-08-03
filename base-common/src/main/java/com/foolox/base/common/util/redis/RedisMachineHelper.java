package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.rediskey.MachinePrefix;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public class RedisMachineHelper {
    /**
     * roomNo是否有对应的machine
     * @param roomNo
     * @return
     */
    public static Boolean existRoomNo(String roomNo) {
        return RedisUtil.exists(MachinePrefix.ROOMNO_MACHINEID, roomNo);
    }

    /**
     * 保存 roomNo 所在的服务器
     * @param roomNo
     * @param machineId
     * @return
     */
    public static void saveRoomNo(String roomNo, String machineId) {
        RedisUtil.set(MachinePrefix.ROOMNO_MACHINEID, roomNo, machineId);
    }
}
