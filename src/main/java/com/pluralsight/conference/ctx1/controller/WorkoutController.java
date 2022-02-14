package com.pluralsight.conference.ctx1.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import com.pluralsight.conference.core.model.Workout;
import com.pluralsight.conference.core.repository.WorkoutRepository;
import com.pluralsight.conference.core.service.ExerciseService;
import com.pluralsight.conference.core.service.UserService;
import com.pluralsight.conference.ctx1.helpers.Helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    UserService userService;

    @Autowired
    Helper helper;

    @Autowired
    WorkoutRepository workoutRepository;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
    }

    @GetMapping("/index")
    public String index(Model model) {
        User user = userService.findFirstByUsername(model.getAttribute("principalName").toString());
        List<Workout> workouts = workoutRepository.getWorkoutsByUser(user);

        model.addAttribute("workouts", workouts);

        return "workouts/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Workout workout = new Workout();

        User user = userService.findFirstByUsername(model.getAttribute("principalName").toString());

        List<Exercise> exercises = user.getExercises();
        Collections.sort(exercises, helper.compareExByDescr);
        
        model.addAttribute("exercises", exercises);
        model.addAttribute("workout", workout);
        return "workouts/create";
    }

    @RequestMapping(value="/store", method=RequestMethod.POST)
    public String store(@Valid @ModelAttribute Workout workout, BindingResult br, Model model, RedirectAttributes redirectAttrs) {
        
        if(br.hasErrors())  
        {  
            return "workouts/create";  
        }  

        // Check that user is adding only his exercises
        Exercise exercise = workout.getExid();
        User currentUser = userService.findFirstByUsername(model.getAttribute("principalName").toString());
        if(!currentUser.getId().equals(exercise.getUser().getId())) {
            redirectAttrs.addFlashAttribute("errors", helper.getLocalizedMsg("wxmessage.cannotAddExercise"));
            return "redirect:/workouts/index";
        }

        workoutRepository.save(workout);
        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("wmessages.successAdded"));
        return "redirect:/workouts/index";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs, Model model) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + id));

        User currentUser = userService.findFirstByUsername(model.getAttribute("principalName").toString());
        if(!currentUser.getId().equals(workout.getExid().getUser().getId())) {
            redirectAttrs.addFlashAttribute("errors", "You can not delete this workout");
            return "redirect:/workouts/index";
        }
        workoutRepository.delete(workout);

        redirectAttrs.addFlashAttribute("success", "Workout successfully deleted");

        return "redirect:/workouts/index";
    }
    
}
