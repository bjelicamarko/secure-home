package com.asdf.myhomeback.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Setter
@NoArgsConstructor
@Entity
public class Device {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @NonNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NonNull
    @Column(name = "photo", nullable = false)
    private String photo;
}
