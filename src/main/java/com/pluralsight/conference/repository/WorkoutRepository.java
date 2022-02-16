package com.pluralsight.conference.repository;

import java.util.List;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.model.Workout;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, Long> {

    @Query("SELECT w FROM Workout w JOIN Exercise e ON w.exid=e.id WHERE e.user = :authuser ORDER BY w.createdat DESC")
    List<Workout> getWorkoutsByUser(@Param("authuser")User user);
}
