package com.pluralsight.conference.ctx2.controller;

import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import com.pluralsight.conference.core.service.ExerciseService;
import com.pluralsight.conference.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/exercise/")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    UserService userService;

    private User currentUser;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        //model.addAttribute("principalName", request.getUserPrincipal().getName());
        //currentUser = userService.findFirstByUsername(model.getAttribute("principalName").toString());
    }

    @RequestMapping(value="/list", method= RequestMethod.POST)
    public List<Exercise> list() {
        //List<Exercise> exercises = exerciseService.findByUserOrderByDescrAsc();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(principal);
        Exercise testExercise = new Exercise();
        testExercise.setDescr("Push-up");
        testExercise.setId(1l);
        List<Exercise> result = new ArrayList<>();
        result.add(testExercise);
        return result;
    }
}
