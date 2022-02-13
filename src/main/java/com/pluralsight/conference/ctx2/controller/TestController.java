package com.pluralsight.conference.ctx2.controller;

import com.pluralsight.conference.core.model.User;
import com.pluralsight.conference.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserService userService;
    
    @GetMapping("/message")
    public User send() {
        User user = userService.findFirstByUsername("demo");
        //user.setUsername("Viktor");
        return user;
        //return "{}";
    }
}
