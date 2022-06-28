package com.asdf.myhomeback.models;

import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.dto.RealEstateWithDevicesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "real_estate")
public class RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "name", unique=true, nullable=false)
    private String name;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "realEstate")
    Set<UserRealEstate> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "real_estate_device",
            joinColumns = @JoinColumn(name = "real_estate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "device_id", referencedColumnName = "id"))
    Set<Device> devices;

    public RealEstate(RealEstateDTO realEstateDTO) {
        this.name = realEstateDTO.getName();
    }

    public RealEstate(RealEstateWithDevicesDTO realEstateDTO) {
        this.name = realEstateDTO.getName();
        this.photo = "/real_estates_photos/house.png";
    }
}
