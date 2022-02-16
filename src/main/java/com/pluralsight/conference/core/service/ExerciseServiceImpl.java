package com.pluralsight.conference.core.service;

import com.pluralsight.conference.core.helpers.CoreHelper;
import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.pluralsight.conference.core.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService  {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    CoreHelper coreHelper;

    @Override
    public Long countByUser(User user) {
        return exerciseRepository.findByUser(user).stream().count();
    }

    @Override
    public List<Exercise> findByUser(User user) {
        return exerciseRepository.findByUser(user);
    }

    @Override
    public List<Exercise> findByUserOrderByDescrAsc(User user) {
        return exerciseRepository.findByUserOrderByDescrAsc(user);
    }

    @Override
    public List<Exercise> findByUserOrderByDescrAscPageable(User user, Integer page) {
        return exerciseRepository.findByUserOrderByDescrAsc(user, PageRequest.of(page, coreHelper.paginationPageSize()));
    };

    @Override
    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }
    
    @Override
    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public void delete(Exercise exercise) {
        exerciseRepository.delete(exercise);
    }
}
