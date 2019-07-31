package com.foolox.base.constant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Configuration
public class TestSwitch {
    @Value("${foolox.test.checkAuth}")
    public static boolean checkAuth;
}
