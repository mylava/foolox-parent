package com.foolox.base.poker.processor;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.strategy.Processor;
import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.annotation.ProcessorType;
import com.foolox.base.constant.annotation.Strategy;
import com.foolox.base.constant.asset.AssetType;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.rediskey.LockPrefix;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.Result;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.db.dao.AssetRepository;
import com.foolox.base.db.domain.Asset;
import com.foolox.base.poker.engine.GameEngine;
import com.foolox.base.poker.domain.Playway;
import com.foolox.base.poker.statemachine.GameEvent;
import com.foolox.base.poker.util.PlaywayContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

/**
 * comment: 大厅创建RedisRoom
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Slf4j
@Strategy(ProcessorType.COMMON_ROOM)
public class CommonRoomProcessor extends Processor {
    //收费类型：钻石、金币、房卡等
    @Value("${foolox.commonRoom.feeType}")
    private String assetType;

    @Value("${foolox.commonRoom.afterCreateJoin}")
    private Boolean afterCreateJoin;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private GameEngine gameEngine;

    @Override
    public void process(Long userId, JSONObject param) {
        String gameType = param.getString("gameType");

        //玩法
        String playTypeExtend = param.getString("playTypeExtend");
        if (StringUtils.isEmpty(playTypeExtend)) {
            log.info("common room create request, playTypeExtend is empty");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("playTypeExtend")));
            return;
        }
        log.info("common room create request, playTypeExtend is [{}]", playTypeExtend);

        Class clz = PlaywayContext.getInstance().getPlaywayClass(GameType.nameOf(gameType));
        Playway playway = (Playway) JSONObject.parseObject(playTypeExtend, clz);

        //STAY 查询余额是否够创建房间
        Asset asset = assetRepository.findByPlayerId(userId, AssetType.nameOf(assetType).getValue());
        if (null==asset || asset.getBalance()<playway.getRoomCost()) {
            log.info("common room create request, playTypeExtend is empty");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("playTypeExtend")));
            return;
        }

        //同一个人是否重复创建房间
        if (!RedisUtil.lockKey(LockPrefix.LOCK_CREATEROOM, userId.toString(), 10)) {
            log.error("userId [{}] repeat request to create room", userId);
            MessageSender.sendToUser(userId, fail(CodeMessage.REPEAT_REQUEST.fillArgs("create room")));
            return;
        }

        Result<GameEvent> result = playway.createRoom(userId, GameType.nameOf(gameType));

        if (result.getCode() == 0) {
            //STAY 添加是否同步到大厅开关
            gameEngine.stateChange(result.getData());
            MessageSender.sendToUser(userId, success(result.getData()));
        } else {
            MessageSender.sendToUser(userId, fail(result.getCode(), result.getMessage()));
        }
    }
}
