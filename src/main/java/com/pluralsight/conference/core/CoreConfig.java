package com.pluralsight.conference.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.pluralsight.conference.core"})
@EnableAutoConfiguration
public class CoreConfig {
    
}
