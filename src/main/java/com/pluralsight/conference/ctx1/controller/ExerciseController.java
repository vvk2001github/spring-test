package com.pluralsight.conference.ctx1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.security.Principal;
import java.util.List;

import com.pluralsight.conference.core.service.*;
import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import com.pluralsight.conference.ctx1.helpers.Helper;

@Controller
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    UserService userService;

    @Autowired
    Helper helper;

    @GetMapping("/exercises/index")
    public String index(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        
        User user = userService.findFirstByUsername(principal.getName());
        List<Exercise> exercises = exerciseService.findByUserid(user);

        model.addAttribute("principalName", principal.getName());
        model.addAttribute("exercises", exercises);
        model.addAttribute("helper", helper);

        return "exercises/index";
    }

    @GetMapping("/exercises/create")
    public String create(HttpServletRequest request, Model model) { 
        model.addAttribute("principalName", request.getUserPrincipal().getName());
        model.addAttribute("exercise", new Exercise());
        return "exercises/create";
    }

    @RequestMapping(value="/exercises/store", method=RequestMethod.POST)
    public String store(HttpServletRequest request, @Valid @ModelAttribute Exercise exercise, BindingResult br) {
        
        if(br.hasErrors())  
        {  
            return "exercises/create";  
        }  
        
        exercise.setUserid(userService.findFirstByUsername(request.getUserPrincipal().getName()));
        this.exerciseService.save(exercise);
        
        return "redirect:/exercises/index";
    }

}
