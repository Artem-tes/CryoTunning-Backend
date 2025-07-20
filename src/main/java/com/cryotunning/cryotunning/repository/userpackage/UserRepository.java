package com.cryotunning.cryotunning.repository.userpackage;

import com.cryotunning.cryotunning.entities.dbentities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    @Query(value = "SELECT * FROM users WHERE username = :username",nativeQuery = true)
    Optional<User> getByUsernameOptional(@Param("username") String username);
}
