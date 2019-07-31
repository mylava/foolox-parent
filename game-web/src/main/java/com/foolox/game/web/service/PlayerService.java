package com.foolox.game.web.service;

import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.db.dao.PlayerRepository;
import com.foolox.base.db.domain.Player;
import com.foolox.game.web.jwt.JwtUtils;
import com.foolox.base.constant.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Player findPlayerById(String id) {
        return playerRepository.findById(id).get();
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> queryAll() {
        return playerRepository.findAll();
    }

    /**
     * 实现用户名密码登录
     * @param
     * @return
     */
    public Result<String> doLogin(String username, String password) {
        Player probe = new Player();
        probe.setUsername(username);
        Example<Player> example = Example.of(probe);
        Player dbPlayer = null;
        try {
            dbPlayer = playerRepository.findOne(example).get();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //不存在的用户
        if (dbPlayer == null) {
            return Result.fail(CodeMessage.LOGIN_USER_NOT_EXIST);
        }
        //密码错误
        if (!dbPlayer.getPassword().equals(password)) {
            return Result.fail(CodeMessage.LOGIN_PASSWORD_INCORRECT);
        }
        //用户被冻结
        if (dbPlayer.getState()==0) {
            return Result.fail(CodeMessage.LOGIN_STATE_INCORRECT);
        }

        String token = JwtUtils.createJWT(dbPlayer);

        //token放入缓存
        RedisPlayerHelper.saveToken(dbPlayer.getId(), token);

//        //生成clientSession
//        ClientSession clientSession = new ClientSession();
//        clientSession.setUserId(dbPlayer.getId());
//        clientSession.setToken(token);
//        clientSession.setNickname(dbPlayer.getUsername());
//        clientSession.setPassword(FooloxUtils.md5(dbPlayer.getPassword()));
//        clientSession.setLogin(true);
//        clientSession.setOnline(false);
//        clientSession.setHeadimg(dbPlayer.getHeadimg());
//
//        //保存clientSession到缓存
//        RedisSessionHelper.saveSession(dbPlayer.getId(), clientSession);

        return Result.success(token);
    }
}
