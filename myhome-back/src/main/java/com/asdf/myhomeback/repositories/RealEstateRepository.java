package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

    @Query("select estate from RealEstate estate where estate.name not in" +
            "(select estate.name from RealEstate estate join UserRealEstate u on estate.id = u.realEstate.id " +
            "where u.user.username = ?1)")
    List<RealEstate> getRealEstateForUserToAssign(String username);
}
