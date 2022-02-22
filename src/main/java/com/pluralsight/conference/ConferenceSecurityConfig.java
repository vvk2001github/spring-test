package com.pluralsight.conference;

import com.pluralsight.conference.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
  prePostEnabled = true, 
  securedEnabled = true, 
  jsr250Enabled = true)
public class ConferenceSecurityConfig  {

    @Autowired
    public UserDetailsService userDetailsService;

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtFilter jwtFilter;

        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**").httpBasic().disable().csrf().disable()
                            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                            .and()
                                    .authorizeRequests()
                                            .antMatchers("/api/auth").permitAll()
                            .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


            //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/css/**", "/", "/auth/**", "/language").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/auth").loginProcessingUrl("/perform_login")
                    .permitAll()
                    .defaultSuccessUrl("/home", true)
                    .and()
                    .logout().deleteCookies("JSESSIONID")
                    .logoutUrl("/perform_logout")
                    .permitAll()
                    .and()
                    .rememberMe().key("uniqueAndSecret");
        }
    }

    @Bean
    public Pbkdf2PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

}
