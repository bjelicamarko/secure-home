package com.asdf.myhomeback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RealEstateWithDevicesDTO extends RealEstateDTO {
    private List<String> devices;
}
