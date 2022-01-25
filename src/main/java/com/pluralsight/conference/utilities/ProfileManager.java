package com.pluralsight.conference.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileManager {

    @Autowired
    Environment env;


    public String getActiveProfiles() {
        for (String profileName : env.getActiveProfiles()) {
            System.out.println("Currently active profile - " + profileName);
        }
        if (env.getActiveProfiles().length == 0) return "Undefined";
        return env.getActiveProfiles()[0];
    }
}
