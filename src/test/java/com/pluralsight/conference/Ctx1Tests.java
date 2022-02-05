package com.pluralsight.conference;

import com.pluralsight.conference.ctx1.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("prod")
@ComponentScan({"com.pluralsight.conference.ctx1"})
@TestPropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class Ctx1Tests {

    //LoginController loginController =new LoginController();

    @Autowired
    LoginController loginController;

    @Test
    public void TestA() {
        assertThat(loginController).isNotNull();
    }
}
