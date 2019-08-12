package com.foolox.game.web.service;

import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.Result;
import com.foolox.base.db.dao.PlayerRepository;
import com.foolox.base.db.domain.Player;
import com.foolox.game.web.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 12/05/2019
 */
@Slf4j
@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * 实现用户名密码登录
     * @param
     * @return
     */
    public Result<String> doLogin(String username, String password) {
        Player dbPlayer = null;
        try {
            dbPlayer = playerRepository.findByUsername(username);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //不存在的用户
        if (null==dbPlayer) {
            return Result.fail(CodeMessage.LOGIN_USER_NOT_EXIST);
        }
        //密码错误
        if (!dbPlayer.getPassword().equals(password)) {
            return Result.fail(CodeMessage.LOGIN_PASSWORD_INCORRECT);
        }
        //用户被冻结
        if (dbPlayer.getStatus()==0) {
            return Result.fail(CodeMessage.LOGIN_STATE_INCORRECT);
        }
        //生成token
        String token = JwtUtils.createJWT(dbPlayer);

        //STAY 放入缓存改为异步保存
        //token放入缓存
        RedisPlayerHelper.saveToken(dbPlayer.getId(), token);
        RedisPlayerHelper.setHeadImg(dbPlayer.getId(), dbPlayer.getHeadimg());
        RedisPlayerHelper.setNickname(dbPlayer.getId(), dbPlayer.getNickname());
        RedisPlayerHelper.setPlayerStatus(dbPlayer.getId(), PlayerStatus.LOGIN.toString());

        return Result.success(token);
    }
}
