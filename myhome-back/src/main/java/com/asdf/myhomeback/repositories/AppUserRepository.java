package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Query("select u from AppUser u where u.id = ?1 and u.userType != 'ROLE_ADMIN' and u.deleted=false" +
            " and u.verified = true and u.accountNonLocked = false")
    Optional<AppUser> findByIdVerifiedButLocked(Long id);

    @Query("select u from AppUser u where u.id = ?1 and not u.userType = 'ROLE_ADMIN' and u.deleted=false" +
            " and u.verified = true and u.accountNonLocked = false")
    Optional<AppUser> findByIdVerifiedUnlocked(Long id);

    @Query("select u from AppUser u where u.username = ?1 and not u.userType = 'ROLE_ADMIN' and u.deleted=false" +
            " and u.verified = true and u.accountNonLocked = false")
    Optional<AppUser> findByUsernameVerifiedUnlocked(String username);

    @Query("select u from AppUser u where not u.userType = 'ROLE_ADMIN' and u.deleted=false")
    Page<AppUser> getAllUsersButAdmin(Pageable pageable);

    @Query("select u from AppUser u " +
            "where u.deleted=false and not u.userType = 'ROLE_ADMIN' and " +
            "( :search = '' " +
            "or lower(u.firstname) like lower(concat('%', :search, '%')) " +
            "or lower(u.lastname) like lower(concat('%', :search, '%'))" +
            "or lower(u.username) like lower(concat('%', :search, '%'))" +
            "or lower(u.email) like lower(concat('%', :search, '%')))" +
            "and (:userType = '' or " +
            "((:userType = 'ROLE_BOTH' and :userType = u.userType)" +
            "or (:userType = 'ROLE_UNASSIGNED' and :userType = u.userType)" +
            "or ((:userType = 'ROLE_TENANT' or :userType = 'ROLE_OWNER') and (:userType = u.userType or u.userType = 'ROLE_BOTH'))))" +
            "and (:verified = '' or (:verified = 'true' and u.verified=true) or (:verified = 'false' and u.verified=false))" +
            "and (:locked = '' or (:locked = 'true' and u.accountNonLocked=false) or (:locked = 'false' and u.accountNonLocked=true))")
    Page<AppUser> searchUsers(@Param("search") String searchField, @Param("userType") String userType, @Param("verified")
                              String verified, @Param("locked") String locked,
                              Pageable pageable);

    Optional<AppUser> findByIdAndDeleted(Long id, boolean deleted);
    
    @Query("UPDATE AppUser u SET u.failedAttempt = ?1 WHERE u.username = ?2")
    @Modifying
    void updateFailedAttempts(int failAttempts, String username);

    Optional<AppUser> findByEmail(String email);

}
