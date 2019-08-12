package com.foolox.base.io.session;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.websocket.server.WsServerAioListener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
public class SessionManager extends WsServerAioListener {

    public static final SessionManager instance = new SessionManager();
    /**
     * 缓存用户id与对应的会话上下文
     */
    private static ConcurrentMap<Long, GroupContext> player2Context = new ConcurrentHashMap<>();

    public static GroupContext getSessionByPlayerId(Long playerId) {
        return player2Context.get(playerId);
    }

    /**
     * 握手成功后，保存会话
     *
     * @param playerId
     * @param groupContext
     * @return
     */
    public boolean registerSession(Long playerId, GroupContext groupContext) {
        player2Context.put(playerId, groupContext);
        log.info("player [{}] registered...", playerId);
        return true;
    }

    /**
     * 断开连接前，删除会话
     *
     * @param channelContext
     * @param throwable
     * @param remark
     * @param isRemove
     * @throws Exception
     */
    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        String playerId = channelContext.getAttribute("playerId").toString();
        player2Context.remove(playerId);
        log.info("before close, remove session success");
        super.onBeforeClose(channelContext, throwable, remark, isRemove);
    }


}
