package com.pluralsight.conference.ctx1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
    }

    @GetMapping("/index")
    public String index() {
        return "workouts/index";
    }
    
}
