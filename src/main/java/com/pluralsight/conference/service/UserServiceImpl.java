package com.pluralsight.conference.service;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("prod")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findFirstByUsername(String userName) {
        return userRepository.findFirstByUsername(userName);
    }

    @PostConstruct
    public void start() {
        System.out.println("UserService profile PROD");
    }
}
