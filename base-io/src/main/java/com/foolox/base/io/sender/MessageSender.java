package com.foolox.base.io.sender;

import com.foolox.base.constant.result.CommonMessage;
import com.foolox.base.io.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.tio.core.Tio;
import org.tio.utils.json.Json;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsResponse;

import java.io.IOException;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 17/07/2019
 */
@Slf4j
public class MessageSender {
    public static void sendToUser(String userId, CommonMessage msg) {
        try {
            log.info("send to client: userId={}, message={}", userId, msg);
            //Event使用cmd代替
            Tio.sendToUser(SessionManager.getSessionByPlayerId(userId), userId, convertToTextResponse(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param message
     * @return
     * @throws IOException
     */
    private static WsResponse convertToTextResponse(CommonMessage message) throws IOException {
        WsResponse response = new WsResponse();
        if (message != null) {
            String json = Json.toJson(message);
            response.setBody(json.getBytes("UTF-8"));
            response.setWsBodyText(json);
            response.setWsBodyLength(response.getWsBodyText().length());
            //返回text类型消息（如果这里设置成 BINARY,那么客户端就需要进行解析了）
            response.setWsOpcode(Opcode.TEXT);
        }
        return response;
    }
}
