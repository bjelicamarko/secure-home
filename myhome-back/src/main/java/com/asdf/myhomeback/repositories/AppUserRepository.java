package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    @Query("select u from AppUser u where not u.username= 'admin' and u.deleted=false")
    Page<AppUser> getAllUsersButAdmin(Pageable pageable);

    @Query("select u from AppUser u where u.deleted=false and not u.username= 'admin' and " +
            "( :search = '' " +
            "or lower(u.firstname) like lower(concat('%', :search, '%')) " +
            "or lower(u.lastname) like lower(concat('%', :search, '%'))" +
            "or lower(u.username) like lower(concat('%', :search, '%'))" +
            "or lower(u.email) like lower(concat('%', :search, '%')))" +
            "and (:userType = '' or u.userType = :userType) ")
    Page<AppUser> searchUsers(@Param("search") String searchField,
                                          @Param("userType") String userType, Pageable pageable);
}
