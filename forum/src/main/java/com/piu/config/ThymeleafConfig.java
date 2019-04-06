package com.piu.config;

import com.piu.thymeleafDialect.DictDialect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    @Bean
    public DictDialect dictDialect() {
        return new DictDialect();
    }
}
