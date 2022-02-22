package com.pluralsight.conference.controller;

import com.pluralsight.conference.repository.UserRepository;
import com.pluralsight.conference.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseService exerciseService;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
    }


    @GetMapping("/home")
    public String home(Model model) {

        return "home/home";
    }
}
