package com.pluralsight.conference.ctx1.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import com.pluralsight.conference.core.model.Workout;
import com.pluralsight.conference.core.service.ExerciseService;
import com.pluralsight.conference.core.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    UserService userService;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
    }

    @GetMapping("/index")
    public String index() {
        return "workouts/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Workout workout = new Workout();

        User user = userService.findFirstByUsername(model.getAttribute("principalName").toString());

        List<Exercise> exercises = user.getExercises();
        Collections.sort(exercises);
        
        model.addAttribute("exercises", exercises);
        model.addAttribute("workout", workout);
        return "workouts/create";
    }
    
}
