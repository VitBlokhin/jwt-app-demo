package me.vitblokhin.jwtappdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String swaggerPage() {
        return "redirect:/swagger-ui.html";
    }
} // class MainController
