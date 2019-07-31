package com.foolox.base.common.loadbalance;

import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.loadbalance.MachineStatus;
import lombok.Data;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Data
public class MachineInfo {
    private String machineId;
    private GameType gameType;
    private MachineStatus status;
    private String ip;
    private int port;
    private long startupTime;
    private long lastUpdateTime;
    private int weiget;
}
