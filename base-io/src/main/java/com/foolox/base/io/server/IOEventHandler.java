package com.foolox.base.io.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.constant.config.TestSwitch;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.CommonMessage;
import com.foolox.base.io.client.FooloxClient;
import com.foolox.base.io.dispatch.MessageDispatcher;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.io.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
public class IOEventHandler implements IWsMsgHandler {
    private IOServer server;
    private MessageDispatcher messageDispatcher;

    public void setServer(IOServer server) {
        this.server = server;
    }

    public void setMessageDispatcher(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String token = httpRequest.getParam("token");
        String playerId = httpRequest.getParam("playerId");
        Tio.bindUser(channelContext, playerId);
        Tio.bindToken(channelContext, token);
        channelContext.setAttribute("playerId", playerId);
        channelContext.setAttribute("token", token);
        SessionManager.instance.registerSession(Long.valueOf(playerId), channelContext.getGroupContext());
        log.info("on handshaked, add session success");
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //鉴权
        if (TestSwitch.checkAuth) {
            String token = channelContext.getAttribute("token").toString();
            Long playerId = Long.valueOf(channelContext.getAttribute("playerId").toString());
            if (!checkAuth(token, playerId)) {
                Tio.remove(channelContext, "receive close flag");
                log.info("after handshaked, close session success, chechAuth: token=[{}], playerId=[{}]", token, playerId);
            }
        }

    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        if (!StringUtils.isEmpty(text)) {
            log.info("onMessage: [{}]", text);
            //在客户端创建，有客户端传入Json进行反序列化生成
            FooloxClient fooloxClient = JSON.parseObject(text, FooloxClient.class);
            fooloxClient.setServer(server);

            String command = fooloxClient.getCommand();
            log.info("receive pact, command is: [{}], message is: [{}]", command, fooloxClient.getMessage());
            if (StringUtils.isEmpty(command)) {
                return null;
            }
            String playerId = channelContext.getAttribute("playerId").toString();

            //更新token过期时间
            RedisPlayerHelper.expireToken(Long.valueOf(playerId));

            //交由controller处理
            messageDispatcher.dispatch(Long.valueOf(playerId), command, JSONObject.parseObject(fooloxClient.getMessage()));
        }
        return null;
    }


    /**
     * 鉴权
     *
     * @return
     */
    private boolean checkAuth(String token, Long playerId) {
        log.info("token={}, playerId={}", token, playerId);
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(playerId)) {
            CodeMessage codeMessage = CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("token", "playerId");
            CommonMessage commonMessage = new CommonMessage("connect", codeMessage);
            MessageSender.sendToUser(playerId, commonMessage);
            return false;
        }
        //校验token
        String redisToken = RedisPlayerHelper.getToken(playerId);

        log.info("redisToken={}", redisToken);
        if (null == redisToken || !token.equals(redisToken)) {
            CodeMessage codeMessage = CodeMessage.HAS_NO_TOKEN_ERROR.fillArgs("token", "playerId");
            CommonMessage commonMessage = new CommonMessage("connect", codeMessage);
            return false;
        }
        return true;
    }

}
