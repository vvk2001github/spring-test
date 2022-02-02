package com.pluralsight.conference.core.repository;

import com.pluralsight.conference.core.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findFirstByUsername(String userName);
}
