package com.asdf.adminback.repositories;

import com.asdf.adminback.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByIdAndAccountNonLocked(Long id, boolean accountNonLocked);

    @Query("UPDATE AppUser u SET u.failedAttempt = ?1 WHERE u.username = ?2")
    @Modifying
    void updateFailedAttempts(int failAttempts, String username);

    Optional<AppUser> findByUsernameAndAccountNonLocked(String username, boolean accountNonLocked);
}
