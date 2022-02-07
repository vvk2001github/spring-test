package com.pluralsight.conference.core;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType;


import com.pluralsight.conference.ctx1.ConferenceConfig;
import com.pluralsight.conference.ctx2.Ctx2Config;

public class ConferenceApplication   {


	//private final Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
	//5756bd2d655c6e5dffe7564f5e7dae18524419792d3cc7dca1e9f33d70227675aaa525bab556c968

	public static void main(String[] args) {

		//SpringApplication.run(ConferenceApplication.class, args);

		new SpringApplicationBuilder().parent(CoreConfig.class)
            .web(WebApplicationType.NONE)
            .child(ConferenceConfig.class)
            .web(WebApplicationType.SERVLET)
			.sibling(Ctx2Config.class)
			.web(WebApplicationType.SERVLET)
            .run(args);

	}


}
