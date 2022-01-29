package com.pluralsight.conference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {

        return "index";

    }
}
