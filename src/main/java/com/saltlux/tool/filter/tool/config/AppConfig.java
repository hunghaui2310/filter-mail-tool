package com.saltlux.tool.filter.tool.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.apache.poi.ss.formula.functions.T;
import java.util.List;

@Configuration
public class AppConfig {

//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClass;
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(100);
        pool.setMaxPoolSize(110);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public ModelMapper modelMapperToString() {
//
//        Converter<List<T>, String> toObjectId = new AbstractConverter<List<T>, String>() {
//            protected String convert(String source) {
//                return source == null ? null : new String(source);
//            }
//        };
//
//        TypeMap<EventMessageModel, Message> typeMap1 = modelMapper.createTypeMap(EventMessageModel.class, Message.class);
//        typeMap1.addMappings(mapper -> {
//            mapper.using(toObjectId)
//                    .map(EventMessageModel::getToUserId, Message::setToUserId);
//
//            mapper.using(toObjectId)
//                    .map(EventMessageModel::getFromUserId, Message::setFromUserId);
//
//        });
//
//        return modelMapper;
//    }
}
