package com.choga3gan.delivery.global.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void trigger(Object event) {
        if (publisher == null) return;
        publisher.publishEvent(event);
    }
}