package com.pluralsight.conference.service;

import com.pluralsight.conference.model.Exercise;
import com.pluralsight.conference.model.User;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    public Long countByUser(User user);
    public void delete(Exercise exercise);
    public Optional<Exercise> findById(Long id);
    List<Exercise> findByUser(User user);
    List<Exercise> findByUserOrderByDescrAsc(User user);
    List<Exercise> findByUserAndTypeOrderByDescrAsc(User user, Integer type);
    List<Exercise> findByUserOrderByDescrAscPageable(User user, Integer page);
    public Exercise save(Exercise exercise);

}
