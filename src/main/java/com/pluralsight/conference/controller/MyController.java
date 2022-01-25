package com.pluralsight.conference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MyController {

    @GetMapping("mytest")
    public String mytest() {
        return "mytest";
    }
}
