package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.service.ExerciseService;
import com.pluralsight.conference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private User currentUser;

    @Autowired
    UserService userService;

    @Autowired
    ExerciseService exerciseService;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
        currentUser = userService.findFirstByUsername(model.getAttribute("principalName").toString());
    }


    @GetMapping("/home")
    public String home(Model model) {

        return "home/home";
    }
}
