package com.insram.worknest.repository.user;

import com.insram.worknest.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    boolean existsByUsername(String userName);
}
