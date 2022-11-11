package com.comfihealth.security.authentication.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query("select c from confirmation_tokens c where c.username = ?1 and c.token = ?2 and c.confirmed = false")
    Optional<ConfirmationToken> findUserConfirmationToken(String username, String token);

    @Transactional
    @Modifying
    @Query("update confirmation_tokens c set c.confirmed = true where c.username = ?1 and c.token = ?2")
    void confirmUserToken(String username, String token);
}
