package com.pluralsight.conference.ctx2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pluralsight.conference.core.model.User;

@RestController
public class TestController {
    
    @GetMapping("/message")
    public User send() {
        return new User("Viktor", "Kulyagin");
    }
}
