package com.choga3gan.delivery.order;

import com.choga3gan.delivery.global.event.Events;
import com.choga3gan.delivery.order.event.OrderAcceptEvent;
import com.choga3gan.delivery.order.event.OrderEventHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("local")
public class OrderEventTest {

    @Autowired
    OrderEventHandler  orderEventHandler;

    @Test
    void test() {
        System.out.println("----------------- event test ------------");
        Events.trigger(new OrderAcceptEvent(UUID.randomUUID()));
    }

}
