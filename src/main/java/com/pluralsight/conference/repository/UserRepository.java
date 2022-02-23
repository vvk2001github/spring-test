package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findFirstByUsername(String userName);
    List<User> findByOrderByUsernameAsc();
}
