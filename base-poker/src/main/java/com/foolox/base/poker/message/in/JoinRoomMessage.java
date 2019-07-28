package com.foolox.base.poker.message.in;

import com.foolox.base.constant.message.Modules;
import com.foolox.base.constant.message.RoomCmd;
import com.foolox.base.constant.annotation.MessageMeta;
import com.foolox.base.io.input.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * comment: 加入房间
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MessageMeta(module = Modules.LOGIN, cmd = RoomCmd.JOIN)
public class JoinRoomMessage extends Message {
}
