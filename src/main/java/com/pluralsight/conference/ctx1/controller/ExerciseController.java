package com.pluralsight.conference.ctx1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import com.pluralsight.conference.core.service.*;
import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import com.pluralsight.conference.ctx1.helpers.Helper;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    UserService userService;

    @Autowired
    Helper helper;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
    }

    @GetMapping("/index")
    public String index(Model model) {
        User user = userService.findFirstByUsername(model.getAttribute("principalName").toString());
        List<Exercise> exercises = exerciseService.findByUseridOrderByDescrAsc(user);

        model.addAttribute("exercises", exercises);
        model.addAttribute("helper", helper);

        return "exercises/index";
    }

    @GetMapping("/create")
    public String create(HttpServletRequest request, Model model) { 
        model.addAttribute("exercise", new Exercise());
        return "exercises/create";
    }

    @RequestMapping(value="/store", method=RequestMethod.POST)
    public String store(@Valid @ModelAttribute Exercise exercise, BindingResult br, Model model, RedirectAttributes redirectAttrs) {
        
        if(br.hasErrors())  
        {  
            return "exercises/create";  
        }  
        
        exercise.setUserid(userService.findFirstByUsername(model.getAttribute("principalName").toString()));
        this.exerciseService.save(exercise);
        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("exmessages.successAdded"));
        return "redirect:/exercises/index";
    }

    @RequestMapping(value = "/{id}/edit")
    public String edit(@PathVariable String id, Model model, RedirectAttributes redirectAttrs) {
        Exercise exercise = exerciseService.findById(Long.valueOf(id)).get();
        User currentUser = userService.findFirstByUsername(model.getAttribute("principalName").toString());

        if(!currentUser.getId().equals(exercise.getUserid().getId())) {
            redirectAttrs.addFlashAttribute("errors", helper.getLocalizedMsg("exmessage.cannotEditExercise"));
            return "redirect:/exercises/index";
        }
        
        model.addAttribute("exercise", exercise);
        return "exercises/edit";
    }

    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String update(@PathVariable Long id, RedirectAttributes redirectAttrs, @Valid @ModelAttribute Exercise exercise, BindingResult br, Model model) {
        
        if(br.hasErrors())  
        {  
            exercise.setId(id);
            return "exercises/edit";  
        }  
        
        exercise.setUserid(userService.findFirstByUsername(model.getAttribute("principalName").toString()));
        this.exerciseService.save(exercise);
        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("exmessages.successUpdated"));
        return "redirect:/exercises/index";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs, Model model) {
        Exercise exercise = exerciseService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid exercise Id:" + id));

        User currentUser = userService.findFirstByUsername(model.getAttribute("principalName").toString());        
        if(!currentUser.getId().equals(exercise.getUserid().getId())) {
            redirectAttrs.addFlashAttribute("errors", helper.getLocalizedMsg("exmessages.cannotDeleteExercise"));
            return "redirect:/exercises/index";
        }
        exerciseService.delete(exercise);

        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("exmessages.successDelete"));

        return "redirect:/exercises/index";
    }

}
