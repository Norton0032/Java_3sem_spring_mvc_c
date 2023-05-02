package com.example.java_3sem_spring_mvc.conrtoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
