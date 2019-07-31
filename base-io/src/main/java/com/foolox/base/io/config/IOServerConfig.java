package com.foolox.base.io.config;

import com.foolox.base.common.loadbalance.MachineInfo;
import com.foolox.base.constant.loadbalance.MachineStatus;
import com.foolox.base.common.util.IpAddrUtil;
import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.loadbalance.MachineHeart;
import com.foolox.base.constant.rediskey.LoadBalancePrefix;
import com.foolox.base.io.server.IOEventHandler;
import com.foolox.base.io.server.IOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
@Configuration
public class IOServerConfig {
    @Value("${foolox.ioServer.port}")
    private Integer port;
    @Value("${foolox.ioServer.timeout}")
    private Long timeout;
    //暂时不用
    @Value("${foolox.ioServer.gameType}")
    private String machineType;
    @Resource
    private IOEventHandler handler;

    private static final String IP = IpAddrUtil.getInnetIp();
    // GameType + IP + 当前进程id
    private String machineId = IP + ":" + ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

    @Bean
    public IOServer socketIOServer() throws IOException {
        IOServer server = new IOServer(port, handler, timeout);
        handler.setServer(server);
        return server;
    }

    //STAY 没有活跃连接时，安全退出
    /**
     * 系统启动后，延迟10秒执行，每隔20秒更新一次IOServer的状态
     */
    @Scheduled(fixedRate = MachineHeart.REDIS_MACHINE_HEART_RATE, initialDelay = 10000)
    public void task() {
        log.info("--------loadbalance task-------------------");
        MachineInfo machineInfo = RedisUtil.get(LoadBalancePrefix.MACHINE, machineId, MachineInfo.class);
        if (null == machineInfo) {
            machineInfo = new MachineInfo();
            machineInfo.setMachineId(machineId);
            machineInfo.setStatus(MachineStatus.RUNNING);
            machineInfo.setIp(IP);
            machineInfo.setPort(port);
            machineInfo.setStartupTime(System.currentTimeMillis());
            machineInfo.setWeiget(10);
        }

        if (machineInfo.getStatus() == MachineStatus.BLOCK) {
            log.info("machine [{}] is waiting for shutdown", machineInfo.getMachineId());
            machineInfo.setWeiget(0);
        }

        machineInfo.setLastUpdateTime(System.currentTimeMillis());
        RedisUtil.set(LoadBalancePrefix.MACHINE, machineId, machineInfo);
    }
}
