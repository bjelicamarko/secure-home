package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceDTO {
    private String name;
    private String photo;

    public DeviceDTO(Device device) {
        this.name = device.getName();
        this.photo = device.getPhoto();
    }

}
