package com.pluralsight.conference;

import com.pluralsight.conference.filter.JwtFilter;
import com.pluralsight.conference.model.CustomOAuth2User;
import com.pluralsight.conference.service.CustomOAuth2UserService;
import com.pluralsight.conference.service.UserServiceImpl;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        @Autowired
        private CustomOAuth2UserService oauth2UserService;

        @Autowired
        private UserServiceImpl userService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/css/**", "/", "/auth/**", "/language", "/oauth2/authorization/facebook").permitAll()
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
                    .and().rememberMe().key("uniqueAndSecret")
                    .and()
                    .oauth2Login().defaultSuccessUrl("/home")
                    .userInfoEndpoint().userService(oauth2UserService)
                    .and()
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

                            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                            userService.processOAuthPostLogin(oAuth2User.getName());
                            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/home");
                        }
                    });
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
