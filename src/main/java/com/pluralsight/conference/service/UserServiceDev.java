package com.pluralsight.conference.service;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("dev")
public class UserServiceDev implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findFirstByUsername(String userName) {
        return userRepository.findFirstByUsername(userName);
    }

    @PostConstruct
    public void start() {
        System.out.println("UserService profile DEV");
        userRepository.save(new User("demo", "1000:a02a9adbfe21de374ec89d2c5593e404:a31cab4bbcbc20ced7960fa24144e49e815683d6ab873ca0c8dc8da89a6de4de66b4cb823c37fb9706ccf33dfc37c2c97c77354bb090c0bf79d03443b56aeb7a"));
        userRepository.save(new User("test", "1000:a02a9adbfe21de374ec89d2c5593e404:a31cab4bbcbc20ced7960fa24144e49e815683d6ab873ca0c8dc8da89a6de4de66b4cb823c37fb9706ccf33dfc37c2c97c77354bb090c0bf79d03443b56aeb7a"));
    }
}
