package com.pluralsight.conference.ctx2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.pluralsight.conference.ctx2")
@PropertySource(value="classpath:ctx2-${spring.profiles.active}.properties")
@EnableAutoConfiguration
public class Ctx2Config {
    
}
