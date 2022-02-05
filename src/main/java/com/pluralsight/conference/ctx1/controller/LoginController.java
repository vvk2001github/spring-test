package com.pluralsight.conference.ctx1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "redirect:/auth";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String auth() {

        return "auth";

    }

}
