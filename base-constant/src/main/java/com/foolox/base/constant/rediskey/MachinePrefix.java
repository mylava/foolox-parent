package com.foolox.base.constant.rediskey;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public class MachinePrefix extends BasePrefix {
    private static final int ONE_DAY = 3600 * 24;

    private MachinePrefix(String prefix) {
        super(ONE_DAY, prefix);
    }

    //roomNo 所在的服务器ID
    public static final MachinePrefix ROOMNO_MACHINEID = new MachinePrefix("machine:roomNo:machineId");
}
