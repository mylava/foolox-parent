package com.foolox.base.poker.facade;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.strategy.GameService;
import com.foolox.base.common.strategy.ServiceFactory;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.constant.asset.AssetType;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.db.dao.AssetRepository;
import com.foolox.base.db.domain.Asset;
import com.foolox.base.io.handler.MessageHandler;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.model.Playway;
import com.foolox.base.poker.util.PlaywayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * comment: 创建房间
 *
 * @author: lipengfei
 * @date: 29/07/2019
 */
@Slf4j
@Facade(value = MessageEvent.CREATE_ROOM)
public class CreateRoomFacade extends MessageHandler {
    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private AssetRepository assetRepository;

    //收费类型：钻石、金币、房卡等
    @Value("${foolox.commonRoom.feeType}")
    private String creatRoomFee;

    @Override
    public void execute(Long playerId, JSONObject message) {
        log.info("player [{}] create room request", playerId);

        //玩家状态 如果已经在房间中，不能创建房间
        PlayerStatus playerStatus = RedisPlayerHelper.getPlayerStatus(playerId);
        log.info("player [{}] status is [{}]", playerId, playerStatus);
        if (!Objects.equals(playerStatus, PlayerStatus.LOGIN)) {
            log.info("player [{}] create room failed, player status is [{}]", playerId, playerStatus);
            MessageSender.sendToUser(playerId, fail(CodeMessage.ILLEGAL_STATUS_ERROR.fillArgs(playerStatus)));
            return;
        }

        //房间类型
        String roomType = message.getString("roomType");
        if (StringUtils.isEmpty(roomType)) {
            log.info("player [{}] create room failed, roomType is null", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("roomType")));
            return;
        }

        //游戏类型
        String gameType = message.getString("gameType");
        if (StringUtils.isEmpty(gameType)) {
            log.info("player [{}] create room request, gameType is empty", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("gameType")));
            return;
        }
        log.info("player [{}] create room request, gameType is [{}]", gameType);

        //STAY 判断gameType是否为合法枚举

        //玩法
        String playTypeExtend = message.getString("playTypeExtend");
        if (StringUtils.isEmpty(playTypeExtend)) {
            log.info("player [{}] create room request, playTypeExtend is empty", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("playTypeExtend")));
            return;
        }
        log.info("player [{}] create room request, playTypeExtend is [{}]", playerId, playTypeExtend);

        Class clz = PlaywayStrategy.getInstance().getPlaywayClassByGameType(GameType.nameOf(gameType));
        Playway playway = (Playway) JSONObject.parseObject(playTypeExtend, clz);

        GameService gameService = serviceFactory.createStrategy(getCommand(), GameType.nameOf(gameType));

        //STAY 配置扣除俱乐部主还是房间创建者
        Asset asset = assetRepository.findByPlayerId(playerId, AssetType.nameOf(creatRoomFee).getValue());
        if (null == asset || asset.getBalance() < gameService.getRoomCost()) {
            log.info("player [{}] create room request, balance is not enough", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.COST_OUT_ERROR));
            return;
        }

        //同一个人是否重复创建房间
        if (!RedisPlayerHelper.lockKey(playerId, 10)) {
            log.error("playerId [{}] repeat request to create room", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.REPEAT_REQUEST.fillArgs("create room")));
            return;
        }

        log.info("before check pass, player [{}] start to [create room]", playerId);
        gameService.createRoom(playerId, GameType.nameOf(gameType), playway);
    }
}
