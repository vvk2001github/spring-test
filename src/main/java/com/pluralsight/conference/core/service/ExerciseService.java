package com.pluralsight.conference.core.service;

import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    public Optional<Exercise> findById(Long id);
    List<Exercise> findByUser(User user);
    List<Exercise> findByUserOrderByDescrAsc(User user);
    public Exercise save(Exercise exercise);
    public void delete(Exercise exercise);
}
