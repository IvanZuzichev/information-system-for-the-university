package com.mpt.journal.service;


import com.mpt.journal.model.UserModel;
import com.mpt.journal.repository.UserReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InMemoryUserServiceImpl implements UserService {

    @Override
    public void deleteUserDeletedIdTrue() {
        userRepository.deleteAllByDeletedIsTrue();
    }

    @Autowired
    private UserReposotory userRepository;

    public InMemoryUserServiceImpl(UserReposotory userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserModel addUser (UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserModel updateUser (UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser (long id) {
        UserModel user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public UserModel FindUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public List<UserModel> findUsersByUsername(String username) {
        return userRepository.findAllByUsername(username);
    }
    public UserModel getCurrentUser () {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }
}
