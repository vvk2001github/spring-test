package com.pluralsight.conference.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pluralsight.conference.model.Exercise;
import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.ExerciseRepository;
import com.pluralsight.conference.service.ExerciseService;
import com.pluralsight.conference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User getUser(HttpServletRequest request) {
        User currentUser = userService.findFirstByUsername(request.getUserPrincipal().getName());
        return currentUser;
    };

    @RequestMapping(value = "/exercises", method = RequestMethod.POST)
    public MappingJacksonValue getExercises(HttpServletRequest request) {

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("workout");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", simpleBeanPropertyFilter);
        List<Exercise> exerciseList = (List<Exercise>) exerciseRepository.findAll();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(exerciseList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    };
}
