package com.pluralsight.conference.controller;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.pluralsight.conference.model.Exercise;
import com.pluralsight.conference.model.User;
import com.pluralsight.conference.model.Workout;
import com.pluralsight.conference.repository.ExerciseRepository;
import com.pluralsight.conference.repository.UserRepository;
import com.pluralsight.conference.repository.WorkoutRepository;
import com.pluralsight.conference.service.ExerciseService;
import com.pluralsight.conference.helpers.Helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    Helper helper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    private User currentUser;

    @ModelAttribute
    public void addAddAtributes(HttpServletRequest request, Model model) {
        model.addAttribute("principalName", request.getUserPrincipal().getName());
        currentUser = userRepository.findFirstByUsername(model.getAttribute("principalName").toString());
    }

    @GetMapping("/index")
    public String index(@RequestParam Optional<Integer> fltExercise, @RequestParam Optional<Integer> srtDate, Model model) {

        //List<Workout> workouts = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Workout> cq = cb.createQuery(Workout.class);
        Root<Workout> workoutRoot = cq.from(Workout.class);

        cq.where(cb.equal(workoutRoot.get("exid").get("user"), currentUser));


        Integer _fltExercise = 0;

        if(fltExercise.isPresent()) {
            if(fltExercise.get() > 0 ) {
                _fltExercise = fltExercise.get();
                cq.where(cb.equal(workoutRoot.get("exid"), _fltExercise));

            }
        }

        Integer _srtDate = 0;
        if(srtDate.isPresent() && srtDate.get() == 1) _srtDate = 1;

        if(_srtDate == 1) {
                cq.orderBy(cb.asc(workoutRoot.get("createdat")));
            } else {
                cq.orderBy(cb.desc(workoutRoot.get("createdat")));
            }


        TypedQuery<Workout> query = entityManager.createQuery(cq);
        List<Workout> workouts = query.getResultList();

        //Should be deleted
        //workouts = workoutRepository.getWorkoutsByUser(currentUser);

        //Get all the exercises that were used in the workouts.
        List<Exercise> usedExercises = exerciseRepository.getUsedExercises(currentUser);

        model.addAttribute("srtDate", _srtDate);
        model.addAttribute("fltExercise", _fltExercise);
        model.addAttribute("workouts", workouts);
        model.addAttribute("usedExercises", usedExercises);

        return "workouts/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Workout workout = new Workout();

        List<Exercise> exercises = currentUser.getExercises();
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
        if(!currentUser.getId().equals(exercise.getUser().getId())) {
            redirectAttrs.addFlashAttribute("errors", helper.getLocalizedMsg("wxmessage.cannotAddExercise"));
            return "redirect:/workouts/index";
        }

        workoutRepository.save(workout);
        redirectAttrs.addFlashAttribute("success", helper.getLocalizedMsg("wmessages.successAdded"));
        return "redirect:/workouts/index";
    }

    @RequestMapping(value = "/{id}/edit")
    public String edit(@PathVariable String id, Model model, RedirectAttributes redirectAttrs) {
        Workout workout = workoutRepository.findById(Long.valueOf(id)).get();

        if(!currentUser.getId().equals(workout.getExid().getUser().getId())) {
            redirectAttrs.addFlashAttribute("errors", "You can not edit this workout");
            return "redirect:/workouts/index";
        }

        List<Exercise> exercises = currentUser.getExercises();
        model.addAttribute("exercises", exercises);
        model.addAttribute("workout", workout);
        return "workouts/edit";
    }

    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String update(@PathVariable Long id, RedirectAttributes redirectAttrs, @Valid @ModelAttribute Workout workout, BindingResult br, Model model) {

        if(br.hasErrors())
        {
            workout.setId(id);
            return "workouts/edit";
        }

        Exercise exercise = workout.getExid();
        if(exercise.getType() == 0) {workout.setWeight1(0f); workout.setCount2(0); workout.setWeight2(0f);};
        if(exercise.getType() == 1) {workout.setWeight1(0f); workout.setWeight2(0f);};
        if(exercise.getType() == 2) {workout.setCount2(0); workout.setWeight2(0f);};

        this.workoutRepository.save(workout);
        redirectAttrs.addFlashAttribute("success", "The workout has been successfully modified.");
        return "redirect:/workouts/index";
    }

    @RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs, Model model) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + id));

        if(!currentUser.getId().equals(workout.getExid().getUser().getId())) {
            redirectAttrs.addFlashAttribute("errors", "You can not delete this workout");
            return "redirect:/workouts/index";
        }
        workoutRepository.delete(workout);

        redirectAttrs.addFlashAttribute("success", "Workout successfully deleted");

        return "redirect:/workouts/index";
    }
    
}
