package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RealEstateWithHouseholdAndDevicesDTO {

    private List<String> household;
    private List<DeviceDTO> devices;

    public RealEstateWithHouseholdAndDevicesDTO(List<String> household, List<Device> devices) {
        this.household = household;
        this.convertDevices(devices);
    }

    private void convertDevices(List<Device> devicesToConvert) {
        this.devices = new ArrayList<>();
        for (Device device : devicesToConvert)
            devices.add(new DeviceDTO(device));
    }

}
