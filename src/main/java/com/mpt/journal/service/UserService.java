package com.mpt.journal.service;

import com.mpt.journal.model.UserModel;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

public interface UserService {
    List<UserModel> findAllUsers();

    UserModel FindUserById(long id);

    UserModel addUser (UserModel user);

    UserModel updateUser (UserModel user);
    void deleteUserDeletedIdTrue();
    void deleteUser (long id);

    void deleteAllUsers();

    List<UserModel> findUsersByUsername(String username);
    UserModel getCurrentUser();
}