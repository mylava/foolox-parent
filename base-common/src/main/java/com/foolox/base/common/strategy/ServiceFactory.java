package com.foolox.base.common.strategy;

import com.foolox.base.constant.annotation.Strategy;
import com.foolox.base.constant.game.GameType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Slf4j
@Component
public class ServiceFactory implements BeanPostProcessor, Ordered {

    //存储所有策略
    private static final Map<GameType, Object> strategyMap = new HashMap<>();

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class<?> clz = bean.getClass();
            Strategy annotation = clz.getAnnotation(Strategy.class);
            if (null != annotation && GameService.class.isAssignableFrom(clz)) {
                strategyMap.put(annotation.type(), bean);
                log.info("-------regist annotation {} -------", bean);
            }
        } catch (Exception e) {
            log.error("dispatcher init error：", e);
        }
        return bean;
    }

    /**
     * 根据客户机构生成相应策略
     *
     * @param
     * @return
     */
    public GameService createStrategy(String command, GameType gameType) {
        GameService gameService = null;
        Object bean = strategyMap.get(gameType);
        if (null == bean) {
            log.error("get gameService strategy [{}] fail, command is [{}]", gameType, command);
            gameService = (GameService) strategyMap.get(gameType.NULL);
        } else {
            gameService = (GameService) bean;
        }
        gameService.setCommand(command);
        return gameService;
    }
}
