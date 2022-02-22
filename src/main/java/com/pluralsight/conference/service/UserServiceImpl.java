package com.pluralsight.conference.service;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Pbkdf2PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(s);

        if (user == null) {
            System.out.println("User not found! " + s);
            throw new UsernameNotFoundException("User " + s + " was not found in the database");
        }

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        grantList.add(authority);

        // !!!!! Has to be deleted !!!!
        if(user.getUsername().equals("demo")) grantList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantList);

        return userDetails;

    }

    public User findByUsernameAndPassword(String username, String password) {
        User user = userRepository.findFirstByUsername(username);
        if(user != null ) {
            if(passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
