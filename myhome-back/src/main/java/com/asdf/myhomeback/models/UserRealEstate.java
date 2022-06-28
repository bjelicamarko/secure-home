package com.asdf.myhomeback.models;


import com.asdf.myhomeback.models.enums.UserRoleEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user_real_estate")
public class UserRealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "real_estate_id")
    @NonNull
    private RealEstate realEstate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable=false)
    @NonNull
    private UserRoleEnum role;
}
