package com.pluralsight.conference.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.pluralsight.conference.model.Exercise;
import com.pluralsight.conference.model.User;
import java.util.List;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    List<Exercise> findByUser(User user);
    List<Exercise> findByUserOrderByDescrAsc(User user);
    List<Exercise> findByUserOrderByDescrAsc(User user, Pageable pageable);

    List<Exercise> findByUserAndTypeOrderByDescrAsc(User user, Integer type);

    @Query("SELECT DISTINCT w.exid FROM Workout w WHERE w.exid.user = :authuser ORDER BY w.exid.descr")
    List<Exercise> getUsedExercises(@Param("authuser")User user);
}
