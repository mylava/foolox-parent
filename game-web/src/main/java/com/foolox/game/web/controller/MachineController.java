package com.foolox.game.web.controller;

import com.foolox.base.common.loadbalance.MachineInfo;
import com.foolox.base.constant.loadbalance.MachineStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.loadbalance.MachineHeart;
import com.foolox.base.constant.rediskey.LoadBalancePrefix;
import com.foolox.base.constant.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Api(value = "MachineController")
@Slf4j
@RestController
@RequestMapping("/api/machine")
public class MachineController {
    @ApiOperation("获取running状态的服务器")
    @RequestMapping(value = "default",method = RequestMethod.GET)
    public Result<MachineInfo> machine() {
        MachineInfo data = null;
        List<MachineInfo> machineInfos = RedisUtil.multiGet(LoadBalancePrefix.MACHINE, "", MachineInfo.class);
        for (MachineInfo machineInfo : machineInfos) {
            if (machineInfo.getStatus()!= MachineStatus.RUNNING) {
                continue;
            }
            if (System.currentTimeMillis()>(machineInfo.getLastUpdateTime()+ MachineHeart.REDIS_MACHINE_HEART_RATE*1.5)) {
                log.warn("machine {},心跳超时,上次心跳: ", machineInfo.getMachineId(), machineInfo.getLastUpdateTime());
                continue;
            }
            data = machineInfo;
            break;
        }
        if (null==data) {
            return Result.fail(CodeMessage.GAME_SERVER_UNAVAILABLE_ERROR);
        } else {
            return Result.success(data);
        }
    }
}
