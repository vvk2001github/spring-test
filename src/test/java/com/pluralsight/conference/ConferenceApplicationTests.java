package com.pluralsight.conference;

import com.pluralsight.conference.core.service.UserService;
import com.pluralsight.conference.core.service.UserServiceImpl;
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
@ComponentScan({"com.pluralsight.conference.core"})
@EnableAutoConfiguration
@TestPropertySource("classpath:application.properties")
class ConferenceApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void userServiceTest() throws Exception{
        assertThat(userService).isInstanceOf(UserServiceImpl.class);
    }
}
