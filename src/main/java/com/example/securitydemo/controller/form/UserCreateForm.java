package com.example.securitydemo.controller.form;

import lombok.Data;

@Data
public class UserCreateForm {

    private String email;
    private String password;
    private String passwordRepeat;
    private String name;
}
