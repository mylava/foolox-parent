package com.foolox.base.poker.processor;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.strategy.Processor;
import com.foolox.base.constant.annotation.ProcessorType;
import com.foolox.base.constant.annotation.Strategy;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.Result;
import com.foolox.base.db.domain.Playway;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.poker.util.PlaywayContext;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void process(String userId, JSONObject param) {
        //游戏类型
        String gameType = param.getString("gameType");
        if (StringUtils.isEmpty(gameType)) {
            log.info("common room create request, gameType is empty");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("gameType")));
        }
        log.info("common room create request, gameType is [{}]", gameType);
        //玩法
        String playTypeExtend = param.getString("playTypeExtend");
        if (StringUtils.isEmpty(playTypeExtend)) {
            log.info("common room create request, playTypeExtend is empty");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("playTypeExtend")));
        }
        log.info("common room create request, playTypeExtend is [{}]", playTypeExtend);

        //STAY 查询余额是否够创建房间


        Class clz = PlaywayContext.getInstance().getPlaywayClass(GameType.nameOf(gameType));

        Playway playway = (Playway) JSONObject.parseObject(playTypeExtend, clz);

        Result result = playway.createRoom();

        if (result.getCode() == 0) {
            //STAY 添加是否同步到大厅开关
            MessageSender.sendToUser(userId, success(result.getData()));
        } else {
            MessageSender.sendToUser(userId, fail(result.getCode(), result.getMessage()));
        }
    }
}
