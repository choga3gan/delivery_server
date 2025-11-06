package com.choga3gan.delivery.global.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class EventConfig {
    @Autowired
    private ApplicationContext ctx;

    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Events.setPublisher(ctx);
    }
}
