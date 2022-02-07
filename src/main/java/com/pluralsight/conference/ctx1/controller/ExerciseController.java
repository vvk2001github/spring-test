package com.pluralsight.conference.ctx1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import com.pluralsight.conference.core.service.*;
import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;

@Controller
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    UserService userService;

    @GetMapping("/exercises/index")
    public String index(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        
        User user = userService.findFirstByUsername(principal.getName());
        List<Exercise> exercises = exerciseService.findByUserid(user);

        model.addAttribute("principalName", principal.getName());
        model.addAttribute("exercises", exercises);

        return "exercises/index";
    }
}