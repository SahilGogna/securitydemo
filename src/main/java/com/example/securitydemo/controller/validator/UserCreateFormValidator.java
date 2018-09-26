package com.example.securitydemo.controller.validator;

import com.example.securitydemo.controller.form.UserCreateForm;
import com.example.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserCreateFormValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    // Check if we can validate the given object
    @Override
    public boolean supports(Class<?> aClass) {
        return UserCreateForm.class.isAssignableFrom(aClass);
    }

    // Now let's validate
    // The "object" is the guy we're going to validate
    // The "errors" is hte return parameter to specify detected errors
    @Override
    public void validate(Object object, Errors errors) {
        UserCreateForm userCreateForm = (UserCreateForm)object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "UserCreateForm.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "UserCreateForm.name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "UserCreateForm.password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordRepeat", "UserCreateForm.passwordRepeat.empty");
        if(!userCreateForm.getPassword().equals(userCreateForm.getPasswordRepeat())) {
            errors.rejectValue("passwordRepeat", "UserCreateForm.passwordRepeat.no_match");
        }
        if (userService.findByEmail(userCreateForm.getEmail()).isPresent()) {
            errors.rejectValue("email", "UserCreateForm.email.exists");
        }
    }
}
