package com.foolox.base.poker.facade;

import com.foolox.base.constant.annotation.MessageMapping;
import com.foolox.base.poker.message.in.GameStatusMessage;
import com.foolox.base.poker.message.in.JoinRoomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * comment: 请求玩家游戏状态
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Slf4j
@Controller
public class GameStatusFacade {
    @MessageMapping
    public void getStatus(String userId, GameStatusMessage message) {
        log.info("{},{}", userId, message);

    }
}
