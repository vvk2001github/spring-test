package com.pluralsight.conference.core.service;

import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    public Long countByUser(User user);
    public void delete(Exercise exercise);
    public Optional<Exercise> findById(Long id);
    List<Exercise> findByUser(User user);
    List<Exercise> findByUserOrderByDescrAsc(User user);
    List<Exercise> findByUserOrderByDescrAscPageable(User user, Integer page);
    public Exercise save(Exercise exercise);

}
