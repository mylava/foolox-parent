package com.foolox.game.web.controller;

import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.Result;
import com.foolox.game.web.service.PlayerService;
import com.foolox.game.web.vo.PlayerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * comment: 登录相关的controller
 *
 * @author: lipengfei
 * @date: 11/05/2019
 */
@Api(value = "PlayerController")
@Slf4j
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerServiceImpl;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<PlayerVo> login(@RequestParam(value = "username", required = true) String username,
                                  @RequestParam(value = "password", required = true) String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs(StringUtils.isEmpty(username)?"username":"password"));
        }

        log.info("request login: username={}, password={}", username, password);
        return playerServiceImpl.doLogin(username, password);
        //STAY 读取系统配置信息，如 游戏模式(大厅、房卡)、房间等待时长、活动信息等
        //STAY 读取AI信息
    }
}
