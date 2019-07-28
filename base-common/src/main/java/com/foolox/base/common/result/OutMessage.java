package com.foolox.base.common.result;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 26/07/2019
 */
public interface OutMessage {
    /**
     * 发送到客户端的指令
     * 通常情况下是Message.buildKey()
     *
     * @return
     */
    String getCommand();

    /**
     * 发送到客户端的指令
     *
     * @param command
     */
    void setCommand(String command);
}
