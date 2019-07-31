package com.foolox.base.io.server;

import com.foolox.base.io.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.io.IOException;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
public class IOServer {
    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;


    public IOServer(int port, IWsMsgHandler wsMsgHandler, Long timeout) throws IOException {
        wsServerStarter = new WsServerStarter(port, wsMsgHandler);
        serverGroupContext = wsServerStarter.getServerGroupContext();
        //协议名字
        serverGroupContext.setName("fooloxGame");
        //设置心跳超时时间 10分钟
        serverGroupContext.setHeartbeatTimeout(timeout);
        //设置AIO监听，用于管理Session
        serverGroupContext.setServerAioListener(SessionManager.instance);
    }

    public void start() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        wsServerStarter.start();
//        wsServerStarter.getTioServer().stop();
        stopWatch.stop();
        log.info("ioServer启动，耗时[{}]毫秒", stopWatch.getTime());
    }
}
