package com.foolox.base.poker.facade;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.util.redis.RedisRoomHelper;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.io.handler.MessageHandler;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.poker.message.GameStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * comment: 请求玩家游戏状态
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Slf4j
@Facade(value = MessageEvent.GAME_STATUS)
public class GameStatusFacade extends MessageHandler {

    @Override
    public void execute(Long playerId, JSONObject message) {
        log.info("{},{}", playerId, message);
        GameStatus gameStatus = new GameStatus();

        gameStatus.setPlayerId(playerId);
        //长连接创建成功，表示鉴权通过，更新玩家状态为就绪（可以游戏）
        gameStatus.setGamestatus(PlayerStatus.LOGIN.toString());

        //查看玩家是否在房间内
        String roomId = RedisRoomHelper.getRoomNoByPlayerId(playerId);
        //Room 不为空，表示已经在游戏中（断线重连情况）
        if (!StringUtils.isBlank(roomId) ) {
            gameStatus.setGamestatus(PlayerStatus.WATCH.toString());
            //读取房间信息
//            GameRoom gameRoom = FooloxUtils.getRoomById(roomId, GameRoom.class);
//            //Board 不为空
//            FooloxUtils.getBoardByRoomId(roomId, Board.class) != null
//
//            //读取玩法信息
//            PlaywayMeta playway = FooloxUtils.getGamePlaywayById(gameRoom.getPlaywayInfo().getPlaywayId());
//            gameStatus.setGametype(playway.getModelCode());
//            gameStatus.setPlayway(playway.getId());
//            //更新玩家状态为游戏中
//            log.info("user {} is playing, gameRoom = {}", playerId, gameRoom);
//            gameStatus.setGamestatus(PlayerStatus.PLAYING.toString());
            //房卡游戏
//            if (gameRoom.getBaseInfo().isCardroom()) {
//                gameStatus.setCardroom(true);
//            }
        }
        //发送gameStatus到客户端
        MessageSender.sendToUser(playerId, success(gameStatus));
    }
}
