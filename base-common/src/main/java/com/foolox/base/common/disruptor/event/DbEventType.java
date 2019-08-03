package com.foolox.base.common.disruptor.event;

/**
 * comment: 数据库写事件类型
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
public enum DbEventType {
    SAVE, UPDATE, DELETE,;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
