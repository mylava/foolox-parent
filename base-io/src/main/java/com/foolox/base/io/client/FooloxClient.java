package com.foolox.base.io.client;

import com.foolox.base.io.server.IOServer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 17/07/2019
 */
@Slf4j
@Data
public class FooloxClient {
    private IOServer server;
    //用于前端传命令
    private String message;
    //用于鉴权
    private String token;
    private String command;
}
