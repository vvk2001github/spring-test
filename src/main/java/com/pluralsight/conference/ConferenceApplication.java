package com.pluralsight.conference;

import com.pluralsight.conference.model.User;
import com.pluralsight.conference.repository.UserRepository;
import com.pluralsight.conference.utilities.MyPassHash;
import com.pluralsight.conference.utilities.ProfileManager;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@SpringBootApplication
public class ConferenceApplication extends SpringBootServletInitializer {

	@Autowired
	public ProfileManager profileManager;

	private Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
	//5756bd2d655c6e5dffe7564f5e7dae18524419792d3cc7dca1e9f33d70227675aaa525bab556c968
	//1000:a02a9adbfe21de374ec89d2c5593e404:a31cab4bbcbc20ced7960fa24144e49e815683d6ab873ca0c8dc8da89a6de4de66b4cb823c37fb9706ccf33dfc37c2c97c77354bb090c0bf79d03443b56aeb7a

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

		SpringApplication.run(ConferenceApplication.class, args);

	}

	@PostConstruct
	public void start() throws Exception {
		this.profileManager.getActiveProfiles();
		System.out.println(passwordEncoder.encode("demo"));
		System.out.println(passwordEncoder.matches("demo", "5756bd2d655c6e5dffe7564f5e7dae18524419792d3cc7dca1e9f33d70227675aaa525bab556c968"));
	}


}
