package com.example.securitydemo.config;

import com.example.securitydemo.controller.form.UserCreateForm;
import com.example.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final UserService userService;

    @Autowired
    public ApplicationStartup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if(userService.findAll().isEmpty()) {
            UserCreateForm userCreateForm = new UserCreateForm();
            userCreateForm.setEmail("mir@gmail.com");
            userCreateForm.setPassword("1234");
            userCreateForm.setPasswordRepeat("1234");
            userService.create(userCreateForm);
        }
    }
}
