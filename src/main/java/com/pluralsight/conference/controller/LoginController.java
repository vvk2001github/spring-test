package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.service.UserService;
import com.pluralsight.conference.utilities.MyPassHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        User user = userService.findFirstByUsername((username));
        if(user == null) return "badpass";

        if (MyPassHash.validatePassword(password, user.getPassword()) == true)  {
            return "home";
        } else {
            return "badpass";
        }
    }
}
