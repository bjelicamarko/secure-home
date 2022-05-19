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
    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @NonNull
    @Column(name = "username", nullable = false)
    private String username;

    @NonNull
    @Column(name = "expired_in", nullable = false)
    private Long expiredIn;
}
