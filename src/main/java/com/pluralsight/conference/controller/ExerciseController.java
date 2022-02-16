package com.pluralsight.conference.controller;

import com.pluralsight.conference.service.ExerciseService;
import com.pluralsight.conference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import com.pluralsight.conference.model.Exercise;
import com.pluralsight.conference.model.User;
import com.pluralsight.conference.helpers.Helper;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    UserService userService;

    @Autowired
    Helper helper;

    private User currentUser;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
        currentUser = userService.findFirstByUsername(model.getAttribute("principalName").toString());
    }

    @GetMapping("/index")
    public String index(@RequestParam Optional<Integer> page, Model model) {
        //pagination start
        Integer currentPage = 1;
        if(page.isPresent()) currentPage = page.get();
        if(currentPage < 1) currentPage = 1;

        Long pageSize = helper.paginationPageSize();
        Long pageLinksCount = helper.paginationRelativeLinksCount();
        Long repoSize = exerciseService.countByUser(currentUser);

        Long lastPage =( repoSize / pageSize ) + 1;
        if((repoSize % pageSize) == 0) lastPage -= 1;
        if(currentPage > lastPage) currentPage = Math.toIntExact(lastPage);

        Long firstForPage = currentPage - pageLinksCount;
        Long lastForPage = currentPage + pageLinksCount;

        if(firstForPage < 1) {
          lastForPage += 1-firstForPage;
          firstForPage = 1l;
        };

        if(lastForPage > lastPage) {
            firstForPage -= lastForPage - lastPage;
            lastForPage = lastPage;
        }

        if(firstForPage < 1) firstForPage = 1l;

        Long prevGroupPage = currentPage - ((helper.paginationRelativeLinksCount() * 2) + 1);
        Long nextGroupPage = currentPage + ((helper.paginationRelativeLinksCount() * 2) + 1);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("firstForPage", firstForPage);
        model.addAttribute("lastForPage", lastForPage);
        model.addAttribute("prevGroupPage", prevGroupPage);
        model.addAttribute("nextGroupPage", nextGroupPage);
        //pagination end


        //List<Exercise> exercises = exerciseService.findByUserOrderByDescrAsc(currentUser);
        System.out.println("ExerciseController Index: currentPage = " + currentPage.toString());
        System.out.println("ExerciseController Index: repoSize = " + repoSize.toString());
        System.out.println("ExerciseController Index: pageSize = " + pageSize.toString());
        System.out.println("ExerciseController Index: lastPage = " + lastPage.toString());
        System.out.println("ExerciseController Index: firstForPage = " + firstForPage.toString());
        System.out.println("ExerciseController Index: lastForPage = " + lastForPage.toString());
        List<Exercise> exercises = exerciseService.findByUserOrderByDescrAscPageable(currentUser, currentPage - 1);

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
        
        exercise.setUser(currentUser);
        this.exerciseService.save(exercise);
        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("exmessages.successAdded"));
        return "redirect:/exercises/index";
    }

    @RequestMapping(value = "/{id}/edit")
    public String edit(@PathVariable String id, Model model, RedirectAttributes redirectAttrs) {
        Exercise exercise = exerciseService.findById(Long.valueOf(id)).get();

        if(!currentUser.getId().equals(exercise.getUser().getId())) {
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
        
        exercise.setUser(currentUser);
        this.exerciseService.save(exercise);
        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("exmessages.successUpdated"));
        return "redirect:/exercises/index";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs, Model model) {
        Exercise exercise = exerciseService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid exercise Id:" + id));

        if(!currentUser.getId().equals(exercise.getUser().getId())) {
            redirectAttrs.addFlashAttribute("errors", helper.getLocalizedMsg("exmessages.cannotDeleteExercise"));
            return "redirect:/exercises/index";
        }
        exerciseService.delete(exercise);

        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("exmessages.successDelete"));

        return "redirect:/exercises/index";
    }

}
