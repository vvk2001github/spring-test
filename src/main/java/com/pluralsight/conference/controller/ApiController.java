package com.pluralsight.conference.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pluralsight.conference.model.Exercise;
import com.pluralsight.conference.model.User;
import com.pluralsight.conference.model.Workout;
import com.pluralsight.conference.repository.ExerciseRepository;
import com.pluralsight.conference.repository.WorkoutRepository;
import com.pluralsight.conference.service.ExerciseService;
import com.pluralsight.conference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserService userService;

    @Autowired
    WorkoutRepository workoutRepository;

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public User getUser(HttpServletRequest request) {
        User currentUser = userService.findFirstByUsername(request.getUserPrincipal().getName());
        return currentUser;
    };

    @RequestMapping(value = {"/api/exercisesbytype", "/int/exercisesbytype"}, method = RequestMethod.POST)
    public MappingJacksonValue getExercisesByType(HttpServletRequest request, @RequestParam Integer extype) {

        User currentUser = userService.findFirstByUsername(request.getUserPrincipal().getName());
        List<Exercise> exerciseList = (List<Exercise>) exerciseRepository.findByUserAndTypeOrderByDescrAsc(currentUser, extype);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("workout");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(exerciseList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    };

    @RequestMapping(value = {"/api/workbyexercise", "/int/workbyexercise"}, method = RequestMethod.POST)
    public MappingJacksonValue findAllByExercise(HttpServletRequest request, @RequestParam Integer exid) {

        Optional<Exercise> exercise = exerciseRepository.findById(Long.valueOf(exid));

        if (exercise.isPresent()) {
            List<Workout> workouts = workoutRepository.findAllByExidOrderByCreatedatAsc(exercise.get());
            SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept();
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", simpleBeanPropertyFilter);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(workouts);
            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        } else {
            return null;
        }

    };
}
