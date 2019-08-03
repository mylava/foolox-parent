package com.foolox.base.poker.service;

import com.foolox.base.common.util.FooloxUtils;
import com.foolox.base.common.util.redis.RedisMachineHelper;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.result.Result;
import com.foolox.base.poker.statemachine.GameEvent;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public abstract class GameService {

    /**
     * 创建房间
     * @return
     */
    public abstract Result<GameEvent> createRoom(long userId, GameType gameType);

    /**
     * 计算创建房间需要的费用
     * @return
     */
    public abstract long getRoomCost();

    /**
     * 生成length长度的随机数作为房间号
     *
     * @param length
     * @return
     */
    protected String newRoomNo(int length) {
        if (length > 1) {
            double random = (Math.random() * 9 + 1);
            int pow = (int) Math.pow(10d, length - 1);
            String roomNo = String.valueOf((int)random * pow);
            // 判断random是否已经被占用
            if (RedisMachineHelper.existRoomNo(roomNo)) {
                return newRoomNo(length);
            } else {
                RedisMachineHelper.saveRoomNo(roomNo, FooloxUtils.MACHINE_ID);
                return roomNo;
            }
        }
        return null;
    }
}
