package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RealEstateWithHouseholdAndDevicesDTO {

    private List<String> household;
    private List<DeviceDTO> devices;

    public RealEstateWithHouseholdAndDevicesDTO(List<String> household, List<Device> devices) {
        this.household = household;
        this.devices = devices.stream().map(DeviceDTO::new).toList();
    }

}
