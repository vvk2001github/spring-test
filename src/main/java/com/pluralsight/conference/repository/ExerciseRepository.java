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

    //@Query("SELECT e FROM Exercise e WHERE (e.user = :authuser)and(e.type = :type) ORDER BY e.descr ASC")
    //List<Exercise> findByUserAndByTypeOrderByDescr(@Param("authuser")User user, @Param("type")Integer type);
    List<Exercise> findByUserAndTypeOrderByDescrAsc(User user, Integer type);
}
