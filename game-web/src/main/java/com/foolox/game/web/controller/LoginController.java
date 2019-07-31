package com.foolox.game.web.controller;

import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.Result;
import com.foolox.game.web.service.PlayerService;
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
@Api(value = "LoginController")
@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private PlayerService playerService;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "default", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs(StringUtils.isEmpty(username)?"username":"password"));
        }

        log.info("request login: username={}, password={}", username, password);
        return playerService.doLogin(username, password);
        //STAY 读取系统配置信息，如 游戏模式(大厅、房卡)、房间等待时长、活动信息等
        //STAY 读取AI信息
    }
}