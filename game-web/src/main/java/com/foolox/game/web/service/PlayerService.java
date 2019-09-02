package com.foolox.game.web.service;

import com.foolox.base.constant.result.Result;
import com.foolox.base.db.domain.Player;
import com.foolox.game.web.vo.PlayerVo;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 13/08/2019
 */
public interface PlayerService {
    /**
     * 普通用户
     * @param username
     * @param password
     * @return
     */
    Result<PlayerVo> doLogin(String username, String password);

    /**
     * 微信登录
     * @param unionid
     * @param code
     * @return
     */
    Result<PlayerVo> doWechatLogin(String unionid, String code);

    /**
     * 通过id获取玩家信息
     * @param playerId
     * @return
     */
    Player getPlayerById(Long playerId);
}
