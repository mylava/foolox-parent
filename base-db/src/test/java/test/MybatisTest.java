package test;

import com.foolox.base.db.dao.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 31/07/2019
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class MybatisTest {
    @Autowired
    PlayerRepository playerRepository;

    @Test
    public void testMybatis() {
        boolean player = playerRepository.findById(123l).isPresent();
        log.info("player={}",player);
    }
}
