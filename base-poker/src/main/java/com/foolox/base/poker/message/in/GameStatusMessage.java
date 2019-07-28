package com.foolox.base.poker.message.in;

import com.foolox.base.constant.annotation.MessageMeta;
import com.foolox.base.constant.message.Modules;
import com.foolox.base.constant.message.PlayerCmd;
import com.foolox.base.io.input.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MessageMeta(module = Modules.PLAYER, cmd = PlayerCmd.STATUS)
public class GameStatusMessage extends Message {
}
