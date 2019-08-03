package test;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.io.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 17/07/2019
 */
@Slf4j
@Facade(MessageEvent.GAME_STATUS)
public class HelloFacade extends MessageHandler {

    @Override
    public void execute(Long userId, JSONObject message) {
        log.info("{},{}", userId, message);
//        MessageSender.sendToUser(userId,"hello",message);
    }
}
