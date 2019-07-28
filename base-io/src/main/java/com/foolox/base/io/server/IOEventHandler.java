package com.foolox.base.io.server;

import com.alibaba.fastjson.JSON;
import com.foolox.base.common.result.CodeMessage;
import com.foolox.base.common.result.CommonError;
import com.foolox.base.common.util.FooloxUtils;
import com.foolox.base.io.client.FooloxClient;
import com.foolox.base.io.dispatch.MessageDispatcher;
import com.foolox.base.io.dispatch.MessageFactory;
import com.foolox.base.io.input.Message;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.io.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.utils.json.Json;
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
        String userId = httpRequest.getParam("userId");
        Tio.bindUser(channelContext, userId);
        Tio.bindToken(channelContext, token);
        channelContext.setAttribute("userId", userId);
        channelContext.setAttribute("token", token);
        SessionManager.instance.registerSession(userId, channelContext.getGroupContext());
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //鉴权
        String token = channelContext.getAttribute("token").toString();
        String userId = channelContext.getAttribute("userId").toString();
        if (!checkAuth(token, userId)) {
            Tio.remove(channelContext, "receive close flag");
            log.info("close connection after chechAuth: token=[{}], userId=[{}]", token, userId);
        }
        log.info("after handshaked, add session success");
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

            short module = fooloxClient.getModule();
            short action = fooloxClient.getAction();
            log.info("module [{}], action [{}]", module, action);

            Class<? extends Message> messageClass = MessageFactory.getInstance().getMessageMeta(module, action);
            Message message = JSON.parseObject(fooloxClient.getMessage(), messageClass);
            log.info("receive pact, message is: [{}]", message.getClass().getSimpleName());

            //交由controller处理
            messageDispatcher.dispatch(fooloxClient.getUserId(), message);
        }
        return null;
    }


    /**
     * 鉴权
     *
     * @return
     */
    private boolean checkAuth(String token, String userId) {
        log.info("token={}, userId={}", token, userId);
        CommonError commonError = new CommonError();
        commonError.setCommand("connect");
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {
            CodeMessage codeMessage = CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("token", "userId");
            commonError.setMessage(codeMessage);
            MessageSender.sendToUser(userId, commonError);
            return false;
        }

        String redisToken = FooloxUtils.getTokenByUserId(userId);
        log.info("redisToken={}", redisToken);
        if (null == redisToken || !token.equals(redisToken)) {
            CodeMessage codeMessage = CodeMessage.HAS_NO_TOKEN_ERROR.fillArgs("token", "userId");
            commonError.setMessage(codeMessage);
            MessageSender.sendToUser(userId, commonError);
            return false;
        }
        return true;
    }

}
