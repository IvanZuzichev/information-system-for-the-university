package com.mpt.journal.repository;

import com.mpt.journal.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserReposotory extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);

    void deleteAllByDeletedIsTrue();

    List<UserModel> findAllByUsername(String username);
}
