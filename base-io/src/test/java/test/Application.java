package test;

import com.alibaba.fastjson.JSON;
import com.foolox.base.io.client.FooloxClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
@EnableScheduling
@ComponentScan(basePackages = {"com.foolox.base.io", "com.foolox.base.common", "test"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        HelloFacade bean = ctx.getBean(HelloFacade.class);
        log.info("HelloFacade = {}", bean);

        FooloxClient client = new FooloxClient();
        Map<String, String> message = new HashMap<>();
        message.put("info", "test message");
        client.setMessage(JSON.toJSONString(message));
        client.setToken("token...");

        String s = JSON.toJSONString(client);
        log.info(s);
    }
}
