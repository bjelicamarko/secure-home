package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.dto.UserRealEstateDTO;
import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.models.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRealEstateRepository extends JpaRepository<UserRealEstate, Long> {

    @Query("UPDATE UserRealEstate u SET u.role = :role WHERE u.user.id = :userId and u.realEstate.id = :realEstateId")
    @Modifying
    void updateRole(@Param("userId") Long userId, @Param("realEstateId") Long realEstateId, @Param("role")UserRoleEnum role);

    @Query("select u from UserRealEstate u where  u.user.id = :userId and u.realEstate.id = :realEstateId")
    UserRealEstate findDuplicate(@Param("userId") Long userId, @Param("realEstateId") Long realEstateId);
}
