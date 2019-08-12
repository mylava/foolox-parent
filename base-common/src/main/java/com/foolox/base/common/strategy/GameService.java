package com.foolox.base.common.strategy;

import com.foolox.base.common.util.FooloxUtils;
import com.foolox.base.common.util.redis.RedisMachineHelper;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.CommonMessage;
import com.foolox.base.db.domain.GameRoom;
import com.foolox.base.model.Playway;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
public abstract class GameService {

    /**
     * 计算创建房间需要的费用
     * @return
     */
    public abstract long getRoomCost();

    /**
     * 创建房间
     * @param playerId
     * @param gameType
     * @param playway
     */
    public abstract void createRoom(long playerId, GameType gameType, Playway playway);

    /**
     *
     * @param playerId
     * @param gameRoom
     */
    public abstract boolean canJoin(long playerId, GameRoom gameRoom);

    /**
     * 加入房间
     * @param playerId
     * @param gameRoom
     */
    public abstract void joinRoom(long playerId, GameRoom gameRoom);



    /**
     * --------------- ---------------
     * 通用方法
     * --------------- ---------------
     */

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public <T> CommonMessage<T> success(T data) {
        return new CommonMessage<T>(this.getCommand(), data);
    }

    public <T> CommonMessage<T> fail(CodeMessage codeMessage) {
        return new CommonMessage<T>(this.getCommand(), codeMessage);
    }

    public <T> CommonMessage<T> fail(int code, String message) {
        return new CommonMessage<T>(this.getCommand(), code, message);
    }

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
