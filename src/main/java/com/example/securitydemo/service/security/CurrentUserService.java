package com.example.securitydemo.service.security;

import com.example.securitydemo.domain.security.CurrentUser;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public boolean canAccessUser(CurrentUser currentUser, Long id) {
        return true;
    }
}
