package com.foolox.base.common.config;

import com.foolox.base.common.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * comment: spring 启动后加载系统资源
 * 在IOC的容器的启动过程，当所有的bean都已经处理完成之后，spring ioc容器会有一个发布事件的动作。
 * 当ioc容器加载处理完相应的bean(即 InitializingBean 接口)之后，给我们提供了一个机会（先有InitializingBean，后有
 * ApplicationListener<ContextRefreshedEvent>），可以去做一些事情。这也是spring ioc容器给提供的一个扩展的地方。
 *
 * @author: lipengfei
 * @date: 26/07/2019
 */
@Slf4j
@Configuration
public class StartedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (ContextUtil.getApplicationContext() == null) {
            ContextUtil.setApplicationContext(event.getApplicationContext());
        }
        log.info("set ContextUtil [{}]", ContextUtil.getApplicationContext());
    }
}
