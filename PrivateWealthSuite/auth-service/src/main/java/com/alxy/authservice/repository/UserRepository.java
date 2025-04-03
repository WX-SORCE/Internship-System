package com.alxy.authservice.repository;

import com.alxy.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
    User findByPhoneNumber(String phoneNumber);

    User findUserByUserId(String userId);

    @Modifying
    @Query("UPDATE User u SET u.identityLevel = :identityLevel, u.status = :status WHERE u.userId = :userId")
    void updateIdentityIdByUserId(@Param("userId") String userId,
                                  @Param("identityLevel") Integer identityLevel,
                                  @Param("status") String status);


}