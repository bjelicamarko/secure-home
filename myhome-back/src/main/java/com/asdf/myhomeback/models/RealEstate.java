package com.asdf.myhomeback.models;

import com.asdf.myhomeback.dto.RealEstateDTO;
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

    @OneToMany(mappedBy = "realEstate")
    Set<UserRealEstate> users;

    public RealEstate(RealEstateDTO realEstateDTO) {
        this.name = realEstateDTO.getName();
    }
}
