package com.pluralsight.conference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class LangController {

    @Autowired
    LocaleResolver localeResolver;

    @GetMapping("/language")
    public String setLanguage(HttpServletRequest request) {
        return "redirect:" + request.getHeader("Referer");
    }
}
