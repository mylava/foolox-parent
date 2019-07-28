package com.foolox.base.io.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Component
public class IOServerStarter implements CommandLineRunner {
    @Resource
    private final IOServer server;

    public IOServerStarter(IOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
