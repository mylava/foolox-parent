package com.foolox.base.constant.annotation;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
public enum ProcessorType {
    //空策略
    NULL,
    //普通房间
    COMMON_ROOM,
    //俱乐部房间
    CLUB_ROOM,;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static ProcessorType nameOf(String name) {
        for (ProcessorType processorType : ProcessorType.values()) {
            if (null != name && name.toLowerCase().equals(processorType.toString())) {
                return processorType;
            }
        }
        return null;
    }
}
