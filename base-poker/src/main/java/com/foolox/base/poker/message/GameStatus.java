package com.foolox.base.poker.message;

import com.foolox.base.constant.game.GameType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/05/2019
 */
@Data
@NoArgsConstructor
public class GameStatus {
    private String gamestatus;
    private long playerId;
    private String roomNo;
    private GameType gametype;
    private String event;
}
