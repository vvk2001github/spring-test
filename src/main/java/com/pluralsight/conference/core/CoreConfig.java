package com.pluralsight.conference.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.pluralsight.conference.core"})
@EntityScan("com.pluralsight.conference.core.model")
@EnableJpaRepositories
@EnableAutoConfiguration
public class CoreConfig {
    
}
