package com.pluralsight.conference.controller;

import javax.servlet.http.HttpServletRequest;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;


    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/index")
    public String index(Model model) {
        Iterable<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        
        return "admin/index";
    }
    
}
