package test;

import com.foolox.base.constant.annotation.MessageMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 17/07/2019
 */
@Slf4j
@Controller
public class HelloFacade {
    @MessageMapping
    public void hello(String userId, HelloMessage message) {
        log.info("{},{}", userId, message);
//        MessageSender.sendToUser(userId,"hello",message);
    }
}
