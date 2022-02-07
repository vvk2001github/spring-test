package com.pluralsight.conference.core.service;

import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pluralsight.conference.core.repository.ExerciseRepository;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService  {

    @Autowired
    ExerciseRepository exerciseRepository;

    public List<Exercise> findByUserid(User userid) {
        return exerciseRepository.findByUserid(userid);
    }
    
}
