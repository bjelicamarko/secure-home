package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findAll();

    @Query("select device from Device device where device.name in ?1")
    Set<Device> findAllByNameInList(List<String> names);
}
