package com.pluralsight.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ConferenceApplication extends SpringBootServletInitializer {

	@Autowired
	Environment env;

	//private final Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
	//5756bd2d655c6e5dffe7564f5e7dae18524419792d3cc7dca1e9f33d70227675aaa525bab556c968

	public static void main(String[] args) {

		SpringApplication.run(ConferenceApplication.class, args);

	}

	@SuppressWarnings("unused")
	@PostConstruct
	public void start() {
		for (String profileName : env.getActiveProfiles()) {
			System.out.println("Currently active profile - " + profileName);
		}
		if (env.getActiveProfiles().length == 0) System.out.println("Currently active profile is Undefined");
	}


}
