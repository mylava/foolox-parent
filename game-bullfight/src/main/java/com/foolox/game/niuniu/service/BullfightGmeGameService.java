package com.foolox.game.niuniu.service;

import com.foolox.base.common.statemachine.GameEvent;
import com.foolox.base.common.strategy.GameService;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.common.util.redis.RedisRoomHelper;
import com.foolox.base.constant.annotation.Strategy;
import com.foolox.base.constant.asset.AssetType;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.game.PlayerAction;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.game.RoomStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.db.dao.AssetRepository;
import com.foolox.base.db.dao.ClubPlayerRepository;
import com.foolox.base.db.domain.Asset;
import com.foolox.base.db.domain.ClubPlayer;
import com.foolox.base.db.domain.GameRoom;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.model.Playway;
import com.foolox.base.poker.engine.GameEngine;
import com.foolox.base.poker.model.GamePlayer;
import com.foolox.base.poker.util.RoomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
@Slf4j
@Strategy(type = GameType.BULLFIGHT)
public class BullfightGmeGameService extends GameService {

    @Autowired
    private GameEngine gameEngine;

    @Autowired
    private ClubPlayerRepository clubPlayerRepository;

    @Autowired
    private AssetRepository assetRepository;

    //此玩法使用的筹码类型
    @Value("${foolox.commonRoom.feeType}")
    private String assetType;

    @Override
    public long getRoomCost() {
        return 0;
    }

    @Override
    public void createRoom(long playerId, GameType gameType, Playway playway) {
        playway.setGameType(gameType);
        //STAY 保存玩法到数据库

        GameRoom room = new GameRoom();
        room.setCreator(playerId);
        room.setRoomNo(newRoomNo(6));
        room.setPlayway(playway);
        room.setGameType(gameType);
        room.setClub(false);

        GameEvent gameEvent = new GameEvent();
        gameEvent.setPlayerId(playerId);
        gameEvent.setRoomNo(room.getRoomNo());
        gameEvent.setPlayerAction(PlayerAction.CREATE);
        gameEvent.setGameRoom(room);

        //STAY 添加是否同步到大厅开关  可以使用枚举，创建不同的发送类型：比如发送到房间，发送到大厅，发送到俱乐部等等
        MessageSender.sendToUser(playerId, success(gameEvent));
        gameEngine.stateChange(gameEvent);
    }

    @Override
    public boolean canJoin(long playerId, GameRoom gameRoom) {
        //房间正在解散中
        if (gameRoom.getRoomStatus() == RoomStatus.DISSOLVE) {
            log.info("player [{}] join room failed, room [{}] is on dissolve", playerId, gameRoom.getRoomNo());
            MessageSender.sendToUser(playerId, fail(CodeMessage.ROOM_ON_DISSOLVE));
            return false;
        }

        Playway playway = gameRoom.getPlayway();
        //房间满员
        if (gameRoom.getCurPersonNum() >= playway.getPlayerNum() + playway.getAudienceNum()) {
            log.info("player [{}] join room failed, room [{}] is full", playerId, gameRoom.getRoomNo());
            MessageSender.sendToUser(playerId, fail(CodeMessage.ROOM_IS_FULL));
            return false;
        }

        //如果房间已经开始游戏了，是否可以中途加入
        if (gameRoom.getCurRound() > 0 && !playway.getCutIn()) {
            log.info("player [{}] join room failed, room cutIn is unable", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.ROOM_IS_FULL));
            return false;
        }

        //俱乐部房间处理
        if (gameRoom.isClub()) {
            log.info("player [{}] join [club] room ");
            ClubPlayer clubplayer = clubPlayerRepository.findByClubIdAndPlayerId(playerId, gameRoom.getClubId());
            if (null == clubplayer) {
                log.info("player [{}] join room failed, player is not in club [{}]", playerId, gameRoom.getClubId());
                MessageSender.sendToUser(playerId, fail(CodeMessage.PLAYER_NOT_IN_CLUB));
                return false;
            }

            Asset asset = assetRepository.findByPlayerId(playerId, AssetType.nameOf(assetType).getValue());
            if (null==asset || asset.getBalance()< playway.getJoinLimit()) {
                log.info("common room create request, playTypeExtend is empty");
                MessageSender.sendToUser(playerId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("playTypeExtend")));
                return false;
            }
        }
        return true;
    }

    @Override
    public void joinRoom(long playerId, GameRoom gameRoom) {

        //玩家已在此房间中
        if (RedisRoomHelper.getRoomPlayerIdList(gameRoom.getRoomNo()).contains(playerId)) {
            log.info("player [{}] join room failed, player already in room [{}]", playerId, gameRoom.getRoomNo());
            MessageSender.sendToUser(playerId, fail(CodeMessage.ROOM_IS_FULL));
            return;
        }
        //加入房间，尚未坐下或准备
        RedisPlayerHelper.setPlayerStatus(playerId, PlayerStatus.WATCH.toString());
        RedisPlayerHelper.setRoomNo(playerId, gameRoom.getRoomNo());
        RedisRoomHelper.addRoomPlayerId(gameRoom.getRoomNo(), playerId);

        GamePlayer gamePlayer = new GamePlayer(playerId);
        gamePlayer.setRoomNo(gameRoom.getRoomNo());
        if (gameRoom.isClub()) {
            //STAY 加上俱乐部关联 每个人在不同俱乐部有不同的筹码
            Asset asset = assetRepository.findByPlayerId(playerId, AssetType.nameOf(assetType).getValue());
            gamePlayer.setTakeIn(asset.getBalance());
        }

        RedisRoomHelper.addRoomPlayer(gameRoom.getRoomNo(), gamePlayer.getPlayerId(), gamePlayer);
        RoomUtil.sendDiffMessage(gameRoom.getRoomNo(), success(gameRoom), success(RedisPlayerHelper.getNickname(playerId)+"加入房间"));

        GameEvent gameEvent = new GameEvent();
        gameEvent.setPlayerId(playerId);
        gameEvent.setRoomNo(gameRoom.getRoomNo());
        gameEvent.setPlayerAction(PlayerAction.JOIN);
        gameEvent.setGameRoom(gameRoom);

        gameEngine.stateChange(gameEvent);
    }


}
