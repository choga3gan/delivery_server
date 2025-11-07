package com.choga3gan.delivery;

import com.choga3gan.delivery.global.event.Events;
import com.choga3gan.delivery.order.event.OrderAcceptEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        Events.trigger(new OrderAcceptEvent(UUID.randomUUID()));
        return "redirect:/swagger-ui/index.html";
    }
}
