package com.foolox.game.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 13/08/2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerVo {
    private long id;

    private String username;

    private String headimg;

    private String nickname;

    private String openid;

    private long balance;

    private String token;
}
