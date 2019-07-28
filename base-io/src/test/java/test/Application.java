package test;

import com.alibaba.fastjson.JSON;
import com.foolox.base.io.client.FooloxClient;
import com.foolox.base.io.dispatch.MessageDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 25/07/2019
 */
@Slf4j
@ComponentScan(basePackages = {"com.foolox.base.io", "com.foolox.base.common", "test"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        HelloFacade bean = ctx.getBean(HelloFacade.class);
        log.info("HelloFacade = {}", bean);

        FooloxClient client = new FooloxClient();
        client.setUserId("1");
        client.setMessage("fasfasd");
        client.setToken("token...");
        client.setModule((short) 101);
        client.setAction((short) 101);


        String s = JSON.toJSONString(client);
        log.info(s);
    }
}
