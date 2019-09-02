package com.foolox.game.web.service;

import com.foolox.base.common.util.FooloxUtils;
import com.foolox.base.common.util.MD5;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.common.util.redis.RedisSystemHelper;
import com.foolox.base.constant.asset.AssetType;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.rediskey.SystemPrefix;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.Result;
import com.foolox.base.constant.sysdic.SysDicCode;
import com.foolox.base.db.dao.AssetRepository;
import com.foolox.base.db.dao.PlayerRepository;
import com.foolox.base.db.dao.SysDicRepository;
import com.foolox.base.db.domain.Asset;
import com.foolox.base.db.domain.Player;
import com.foolox.game.web.jwt.JwtUtils;
import com.foolox.game.web.vo.PlayerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 12/05/2019
 */
@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private SysDicRepository sysDicRepository;

    //收费类型：钻石、金币、房卡等
    @Value("${foolox.commonRoom.feeType}")
    private String feeType;

    /**
     * 实现用户名密码登录
     *
     * @param
     * @return
     */
    @Override
    public Result<PlayerVo> doLogin(String username, String password) {
        Player dbPlayer = null;
        try {
            dbPlayer = playerRepository.findByUsername(username);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //不存在的用户
        if (null == dbPlayer) {
            return Result.fail(CodeMessage.LOGIN_USER_NOT_EXIST);
        }
        //密码错误
        if (!FooloxUtils.md5(dbPlayer.getPassword()).equals(password)) {
            return Result.fail(CodeMessage.LOGIN_PASSWORD_INCORRECT);
        }
        //用户被冻结
        if (null==dbPlayer.getStatus() || dbPlayer.getStatus() == 0) {
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

        PlayerVo vo = new PlayerVo();
        vo.setId(dbPlayer.getId());
        vo.setToken(token);
        vo.setHeadimg(dbPlayer.getHeadimg());
        vo.setNickname(dbPlayer.getNickname());
        vo.setUsername(dbPlayer.getUsername());
        vo.setOpenid(dbPlayer.getOpenid());

        Asset asset = assetRepository.findByPlayerId(dbPlayer.getId(), AssetType.nameOf(feeType));
        vo.setBalance(null == asset ? 0 : asset.getBalance());

        return Result.success(vo);
    }


    @Override
    public Result<PlayerVo> doWechatLogin(String unionid, String code) {
        Player dbPlayer = null;
        //已经用微信登录过的用户
        if (!StringUtils.isEmpty(unionid)) {
            try {
                dbPlayer = playerRepository.findByOpenid(unionid, 1);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            //不存在的用户
            if (null == dbPlayer) {
                return Result.fail(CodeMessage.LOGIN_USER_NOT_EXIST);
            }
            //用户被冻结
            if (dbPlayer.getStatus() == 0) {
                return Result.fail(CodeMessage.LOGIN_STATE_INCORRECT);
            }
        } else if (!StringUtils.isEmpty(code)) { //第一次用微信登录的用户
            String wxappid = RedisSystemHelper.getSysCodeValue(SysDicCode.WX_APP_ID.getCode());
            String wxappsecret = RedisSystemHelper.getSysCodeValue(SysDicCode.WX_APP_SECRET.getCode());
        }



        try {
            dbPlayer = playerRepository.findByOpenid(unionid, 1);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //不存在的用户
        if (null == dbPlayer) {
            return Result.fail(CodeMessage.LOGIN_USER_NOT_EXIST);
        }

        //用户被冻结
        if (dbPlayer.getStatus() == 0) {
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

        PlayerVo vo = new PlayerVo();
        vo.setId(dbPlayer.getId());
        vo.setToken(token);
        vo.setHeadimg(dbPlayer.getHeadimg());
        vo.setNickname(dbPlayer.getNickname());
        vo.setUsername(dbPlayer.getUsername());
        vo.setOpenid(dbPlayer.getOpenid());

        Asset asset = assetRepository.findByPlayerId(dbPlayer.getId(), AssetType.nameOf(feeType));
        vo.setBalance(null == asset ? 0 : asset.getBalance());

        return Result.success(vo);
    }

    @Override
    public Player getPlayerById(Long playerId) {
        if (null==playerId || 0==playerId) {
            return null;
        }

        Optional<Player> optional = playerRepository.findById(playerId);
        return optional.orElse(null);
    }
}
