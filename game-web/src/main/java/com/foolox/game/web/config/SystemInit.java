package com.foolox.game.web.config;

import com.foolox.base.common.util.redis.RedisSystemHelper;
import com.foolox.base.db.dao.SysDicRepository;
import com.foolox.base.db.domain.SysDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 04/08/2019
 */
@Component
public class SystemInit implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private SysDicRepository sysDicRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadSystemDic();
    }

    private void loadSystemDic() {
        Iterable<SysDic> sysDics = sysDicRepository.findAll();
        for (SysDic sysDic : sysDics) {
            RedisSystemHelper.setSysCodeValue(sysDic.getCode(), sysDic.getValue());
        }
    }
}
