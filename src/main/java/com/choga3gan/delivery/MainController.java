package com.choga3gan.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }
}
