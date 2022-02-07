package com.pluralsight.conference.ctx2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/message")
    public String send() {
        //return new User("Viktor", "Kulyagin");
        return "{}";
    }
}
