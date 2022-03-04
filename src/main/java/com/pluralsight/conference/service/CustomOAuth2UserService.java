package com.pluralsight.conference.service;

import com.pluralsight.conference.helpers.Provider;
import com.pluralsight.conference.model.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserServiceImpl userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        //Check Facebook, or Github, or etc
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        Provider loginProvider = Provider.valueOf(registrationId.toUpperCase());

        OAuth2User user = super.loadUser(oAuth2UserRequest);
        CustomOAuth2User oAuth2User = new CustomOAuth2User(user);
        userService.processOAuthPostLogin(oAuth2User.getName(), loginProvider);
        return oAuth2User;
    }
}
