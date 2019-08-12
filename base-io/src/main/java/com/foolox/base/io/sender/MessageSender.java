package com.foolox.base.io.sender;

import com.foolox.base.constant.result.CommonMessage;
import com.foolox.base.io.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.tio.core.Tio;
import org.tio.utils.json.Json;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsResponse;

import java.io.IOException;
import java.util.List;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 17/07/2019
 */
@Slf4j
public class MessageSender {
    /**
     * 单发消息
     * @param playerId
     * @param msg
     */
    public static void sendToUser(Long playerId, CommonMessage msg) {
        try {
            log.info("send [single message] to client: playerId={}, message={}", playerId, msg);
            //Event使用cmd代替
            Tio.sendToUser(SessionManager.getSessionByPlayerId(playerId), playerId.toString(), convertToTextResponse(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     * @param playerIds
     * @param msg
     */
    public static void sendToGroup(List<Long> playerIds, CommonMessage msg) {
        try {
            for (Long playerId : playerIds) {
                log.info("send [group message] to client: playerId={}, message={}", playerId, msg);
                Tio.sendToUser(SessionManager.getSessionByPlayerId(playerId), playerId.toString(), convertToTextResponse(msg));
            }

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
