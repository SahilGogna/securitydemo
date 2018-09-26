package com.example.securitydemo.service;

import com.example.securitydemo.controller.form.UserCreateForm;
import com.example.securitydemo.domain.User;
import com.example.securitydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.getOne(id));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public Collection<User> findAll() {
        return userRepository.findAll(new Sort("email"));
    }

    @Transactional
    public User create(UserCreateForm userCreateForm) {
        User user = new User();
        user.setEmail(userCreateForm.getEmail());
        user.setPassword(userCreateForm.getPassword());
        user.setName(userCreateForm.getName());
        return userRepository.save(user);
    }
}
