package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

}
