package com.pluralsight.conference.service;

import com.pluralsight.conference.model.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User findFirstByUsername(String userName);
}
