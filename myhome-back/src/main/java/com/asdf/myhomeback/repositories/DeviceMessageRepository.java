package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.DeviceMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceMessageRepository extends JpaRepository<DeviceMessage, Long> {
}
