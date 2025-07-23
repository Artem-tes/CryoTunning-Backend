package com.cryotunning.cryotunning.repository.userpackage;

import com.cryotunning.cryotunning.entities.dbentities.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountUserRepository extends JpaRepository<LoginUser,Long> {
    @Query(value = "SELECT COUNT(*) FROM login_users",nativeQuery = true)
    Integer getCountLoginUser();
}
