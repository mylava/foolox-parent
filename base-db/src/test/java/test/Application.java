package test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
@EnableScheduling
@ComponentScan(basePackages = {"com.foolox.base.db", "test"})
@EnableJpaRepositories(basePackages = {"com.foolox.base.db.dao"})
@EntityScan(basePackages = {"com.foolox.base.db.domain"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
