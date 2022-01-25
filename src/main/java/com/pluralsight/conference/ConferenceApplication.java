package com.pluralsight.conference;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.UserRepository;
import com.pluralsight.conference.utilities.MyPassHash;
import com.pluralsight.conference.utilities.ProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@SpringBootApplication
public class ConferenceApplication extends SpringBootServletInitializer {

	@Autowired
	public ProfileManager profileManager;

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

		SpringApplication.run(ConferenceApplication.class, args);

	}

	@PostConstruct
	public void start() throws Exception {
		this.profileManager.getActiveProfiles();
	}


}
