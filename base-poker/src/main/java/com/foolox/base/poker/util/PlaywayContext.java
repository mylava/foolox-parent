package com.foolox.base.poker.util;

import com.foolox.base.constant.annotation.PlaywayMeta;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.db.domain.Playway;
import com.foolox.base.io.util.ClassScanner;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Slf4j
public class PlaywayContext {
    private ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    //存储所有策略
    private static final Map<GameType, Class> playwayMap = new HashMap<>();

    //静态内部类实现单例模式
    private PlaywayContext() {
        init();
    }

    public static PlaywayContext getInstance() {
        return PlaywayContextInstance.instance;
    }

    private static class PlaywayContextInstance {
        private static PlaywayContext instance = new PlaywayContext();
    }

    //在工厂初始化时要初始化策略列表
    private void init() {
        //获取到包下所有的class文件
        Set<Class<?>> classes = ClassScanner.listClassesWithAnnotation("com.foolox.base.db.domain", PlaywayMeta.class);
        for (Class<?> clz : classes) {
            if (Playway.class.isAssignableFrom(clz)) {
                PlaywayMeta annotation = clz.getAnnotation(PlaywayMeta.class);
                playwayMap.put(annotation.value(), clz);
                log.info("load playway [{}] sucess", clz.getSimpleName());
            } else {
                log.error("load playway [{}] fail", clz.getSimpleName());
            }
        }
    }

    public Class getPlaywayClass(GameType gameType) {
        Class clz = playwayMap.get(gameType);
        if (null==clz) {
            log.error("get playway failed, gameType is [{}]", gameType);
        }
        return clz;
    }
}
