package com.pluralsight.conference.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pluralsight.conference.helpers.AuthRequest;
import com.pluralsight.conference.helpers.AuthResponse;
import com.pluralsight.conference.helpers.JwtHelper;
import com.pluralsight.conference.model.Exercise;
import com.pluralsight.conference.model.User;
import com.pluralsight.conference.model.Workout;
import com.pluralsight.conference.repository.ExerciseRepository;
import com.pluralsight.conference.repository.UserRepository;
import com.pluralsight.conference.repository.WorkoutRepository;
import com.pluralsight.conference.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userDetailsService;

    @Autowired
    WorkoutRepository workoutRepository;

    @RequestMapping(value = {"/api/auth"}, method = RequestMethod.POST)
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userDetailsService.findByUsernameAndPassword(request.getLogin(), request.getPassword());
        String token = jwtHelper.generateToken(user.getUsername());

        return new AuthResponse(token);
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public MappingJacksonValue getUser(HttpServletRequest request) {
        User currentUser = userRepository.findFirstByUsername(request.getUserPrincipal().getName());

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("exercises", "password");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(currentUser);
        mappingJacksonValue.setFilters(filterProvider);


        return mappingJacksonValue;
    };

    @RequestMapping(value = {"/api/exercisesbytype", "/int/exercisesbytype"}, method = RequestMethod.POST)
    public MappingJacksonValue getExercisesByType(HttpServletRequest request, @RequestParam Integer extype) {

        User currentUser = userRepository.findFirstByUsername(request.getUserPrincipal().getName());
        List<Exercise> exerciseList = (List<Exercise>) exerciseRepository.findByUserAndTypeOrderByDescrAsc(currentUser, extype);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("workout");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("exFilter", simpleBeanPropertyFilter);
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
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("exFilter", simpleBeanPropertyFilter);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(workouts);
            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        } else {
            return null;
        }

    };


}
