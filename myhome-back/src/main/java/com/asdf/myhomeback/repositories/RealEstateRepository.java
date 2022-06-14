package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

    @Query("select estate from RealEstate estate where estate.name not in" +
            "(select estate.name from RealEstate estate join UserRealEstate u on estate.id = u.realEstate.id " +
            "where u.user.username = ?1)")
    List<RealEstate> getRealEstateForUserToAssign(String username);

    RealEstate findByName(String name);

    @Query("select estate from RealEstate estate join UserRealEstate u on estate.id = u.realEstate.id where u.user.username = ?1")
    Page<RealEstate> getRealEstatesOfUser(String username, Pageable pageable);

    Page<RealEstate> findAll(Pageable pageable);

    @Query("select estate.devices from RealEstate estate where estate.name = ?1")
    List<Device> findDevicesByRealEstateName(String name);

    @Query("select estate from RealEstate estate join fetch estate.devices d where d.name = ?1")
    List<RealEstate> getRealEstatesByDeviceName(String deviceName);

    RealEstate findRealEstateByName(String name);
}
