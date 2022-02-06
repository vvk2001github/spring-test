package com.pluralsight.conference.ctx1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ExerciseController {

    @GetMapping("/exercises")
    public String exercises(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        model.addAttribute("principalName", principal.getName());
        return "exercises/exercises";
    }
}