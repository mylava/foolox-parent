package com.foolox.base.common.util;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 26/07/2019
 */
public class MessageKey {
    public static String buildKey(short module, short cmd) {
        return module + "_" + cmd;
    }
}
