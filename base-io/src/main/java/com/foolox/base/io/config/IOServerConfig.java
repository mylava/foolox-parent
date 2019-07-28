package com.foolox.base.io.config;

import com.foolox.base.io.server.IOEventHandler;
import com.foolox.base.io.server.IOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Configuration
public class IOServerConfig {
    @Value("${foolox.ioServer.port}")
    private Integer port;
    @Resource
    private IOEventHandler handler;

    @Bean
    public IOServer socketIOServer() throws IOException {
        IOServer server = new IOServer(port , handler) ;
        handler.setServer(server);
        return server;
    }
}
