package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.models.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRealEstateRepository extends JpaRepository<UserRealEstate, Long> {

    @Query("select u from UserRealEstate u where u.user.username = :username and u.realEstate.id = :realEstateId")
    UserRealEstate findDuplicate(@Param("username") String username, @Param("realEstateId") Long realEstateId);

    @Query("select u.realEstate from UserRealEstate u where u.user.username = :username")
    List<String> findNamesWhereUserIsAssigned(@Param("username") String username);

    @Query("select u from UserRealEstate u where u.user.username = :username")
    List<UserRealEstate> findUserRealEstateByUsername(@Param("username") String username);

    @Query("select count(u) from UserRealEstate u where  u.user.id = :userId and u.role = :role")
    int findCountOfUserRole(Long userId, UserRoleEnum role);

    @Query("select count(u) from UserRealEstate u where  u.realEstate.id = :estateId and u.role = 'OWNER'")
    int countOwnersOfEstate(@Param("estateId") Long estateId);

    @Modifying
    @Query("delete from UserRealEstate u where u.user.id = :userId and u.realEstate.id = :estateId")
    void deleteUserRealEstate(@Param("userId") Long userId,@Param("estateId") Long estateId);
}
