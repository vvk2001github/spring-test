package com.pluralsight.conference.core.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import java.util.List;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    List<Exercise> findByUser(User user);
    List<Exercise> findByUserOrderByDescrAsc(User user);
    List<Exercise> findByUserOrderByDescrAsc(User user, Pageable pageable);
}
