package com.mpt.journal.controllerApi;


import com.mpt.journal.model.UserModel;
import com.mpt.journal.repository.UserReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserControllerApi {

    @Autowired
    private UserReposotory userRepository;

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserModel createUser (@RequestBody UserModel user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser (@PathVariable Long id, @RequestBody UserModel userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());
                    user.setDeleted(userDetails.isDeleted());
                    user.setRole(userDetails.getRole());
                    UserModel updatedUser  = userRepository.save(user);
                    return ResponseEntity.ok().body(updatedUser );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser (@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
