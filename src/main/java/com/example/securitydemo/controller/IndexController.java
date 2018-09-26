package com.example.securitydemo.controller;

import com.example.securitydemo.controller.form.UserCreateForm;
import com.example.securitydemo.controller.validator.UserCreateFormValidator;
import com.example.securitydemo.domain.User;
import com.example.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class IndexController {

    private final UserService userService;
    private final UserCreateFormValidator userCreateFormValidator;

    @Autowired
    public IndexController(UserService userService, UserCreateFormValidator userCreateFormValidator) {
        this.userService = userService;
        this.userCreateFormValidator = userCreateFormValidator;
    }

    @InitBinder("userCreateForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userCreateFormValidator);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerController_GET(UserCreateForm userCreateForm) {
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/test";
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerController_POST(@Valid UserCreateForm userCreateForm,
                                          BindingResult bindingResult) {
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/test";
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.create(userCreateForm);
        } catch(DataIntegrityViolationException e) {
            bindingResult.reject("UserCreateForm.email.exists", "Email already exists!");
            return "register";
        }
        String email = userCreateForm.getEmail();
        String password = userCreateForm.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(token); // manual authentication
        return "redirect:/test";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testController_GET() {
        return "test";
    }
}
