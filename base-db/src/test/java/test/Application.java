package test;

import com.foolox.base.db.dao.PlayerRepository;
import com.foolox.base.db.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 05/05/2019
 */
@Slf4j
@ComponentScan(basePackages = {"com.foolox.base.db", "test"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        PlayerRepository playerRepository = ctx.getBean(PlayerRepository.class);
        List<Player> all = playerRepository.findAll();
        log.info("player= {}", all);
    }
}
