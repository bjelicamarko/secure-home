package com.asdf.myhomeback.models;

import lombok.*;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Setter
@NoArgsConstructor
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @NonNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
