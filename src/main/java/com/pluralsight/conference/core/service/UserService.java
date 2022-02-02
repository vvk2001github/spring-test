package com.pluralsight.conference.core.service;

import com.pluralsight.conference.core.model.User;

public interface UserService {
    User findFirstByUsername(String userName);
}
