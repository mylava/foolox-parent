package com.foolox.base.poker.domain;

import com.foolox.base.common.util.FooloxUtils;
import com.foolox.base.common.util.redis.RedisMachineHelper;
import com.foolox.base.common.util.redis.RedisRoomHelper;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.result.Result;
import com.foolox.base.poker.statemachine.GameEvent;
import com.foolox.base.poker.util.RoomContext;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
public abstract class Playway {
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
     * 根据规则检查是否可以加入房间
     * 1.是否满员 2.是否可以中途加入
     * @param room
     * @return
     */
    public abstract boolean canJoin(GameRoom room);

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
