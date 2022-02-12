package com.pluralsight.conference.core.repository;

import com.pluralsight.conference.core.model.Workout;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    
}
