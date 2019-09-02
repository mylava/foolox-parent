package com.foolox.base.constant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * comment: 方便测试调整一些校验开关常量
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Configuration
public class TestSwitch {
    @Value("${foolox.constant.checkAuth}")
    public static boolean checkAuth;

}
