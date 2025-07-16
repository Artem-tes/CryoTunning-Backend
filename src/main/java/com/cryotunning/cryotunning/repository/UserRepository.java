package com.cryotunning.cryotunning.repository;

import com.cryotunning.cryotunning.entities.dbentities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    Optional<User> findByUsernameOptional(String username);
}
