package com.pluralsight.conference.core.service;

import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import java.util.List;

public interface ExerciseService {
    List<Exercise> findByUserid(User userid);
    public Exercise save(Exercise exercise);
}
