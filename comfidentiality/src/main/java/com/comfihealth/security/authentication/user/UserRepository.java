package com.comfihealth.security.authentication.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from users u where u.username = ?1")
    Optional<User> findUserByUsername(String username);

    @Transactional
    @Modifying
    @Query("update users u set u.enabled = true where u.username = ?1")
    void enableUser(String username);

    @Transactional
    @Modifying
    @Query("update users u set u.phoneNumberVerifiedAt = ?2 where u.username = ?1")
    void setUserPhoneNumberVerified(String username, LocalDateTime verificationTime);
}
