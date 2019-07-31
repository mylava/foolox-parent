package com.foolox.base.poker.message;

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
    private String userid;
    private String gametype;
    private String event;
}
