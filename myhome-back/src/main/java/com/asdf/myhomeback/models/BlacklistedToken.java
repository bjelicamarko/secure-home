package com.asdf.myhomeback.models;

import lombok.*;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Setter
@NoArgsConstructor
@Entity
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @NonNull
    @Column(columnDefinition="text", length=370, name = "token", unique = true, nullable = false)
    private String token;
}
