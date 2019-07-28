package test;

import com.foolox.base.db.dao.PlayerRepository;
import com.foolox.base.db.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * comment:
 *
 * @author: lipengfei
 * @date: 17/05/2019
 */
@Slf4j
@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class LoginMongoTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void test() {
        List<Player> all = playerRepository.findAll();
        log.info("player= {}", all);
    }
}
