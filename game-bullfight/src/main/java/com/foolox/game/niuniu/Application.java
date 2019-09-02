package com.foolox.game.niuniu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/08/2019
 */
@Slf4j
@EnableScheduling
@ComponentScan(basePackages = {"com.foolox.game.niuniu", "com.foolox.base.common","com.foolox.base.io","com.foolox.base.poker"})
@EnableJpaRepositories(basePackages = {"com.foolox.base.db.dao"})
@EntityScan(basePackages = {"com.foolox.base.db.domain"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("bullfight server started");
    }
}
