package com.pluralsight.conference.core.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.pluralsight.conference.core.model.Exercise;
import com.pluralsight.conference.core.model.User;
import java.util.List;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    List<Exercise> findByUserid(User userid);
    
}
