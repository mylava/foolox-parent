package com.foolox.base.db.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * comment: 配置mongo不保存_class字段
 *
 * @author: lipengfei
 * @date: 17/05/2019
 */
@Configuration
public class MongoMappingConfig {
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
                                                       MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, context);
        try {
            converter.setCustomConversions(beanFactory.getBean(MongoCustomConversions.class));
//            converter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //don't save column _class to mongo collection
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
}
