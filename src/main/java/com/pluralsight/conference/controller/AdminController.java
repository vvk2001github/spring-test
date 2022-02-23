package com.pluralsight.conference.controller;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


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
        List<User> users = userRepository.findByOrderByUsernameAsc();

        model.addAttribute("users", users);
        
        return "admin/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/storeuser", method = RequestMethod.POST)
    public String storeUser(@RequestParam String username, RedirectAttributes redirectAttrs) {
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);
        redirectAttrs.addFlashAttribute("success", "User successfully created");
        return "redirect:/admin/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "deleteuser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam Integer userid, RedirectAttributes redirectAttrs) {
        Optional<User> user = userRepository.findById(Long.valueOf(userid));
        if(user.isPresent()) {
            userRepository.delete(user.get());
            redirectAttrs.addFlashAttribute("success", "User successfully deleted!");
        } else {
            redirectAttrs.addFlashAttribute("error", "User not found.");
        }
        return "redirect:/admin/index";
    }
    
}
