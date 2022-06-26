package com.asdf.adminback.repositories;

import com.asdf.adminback.models.CSR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CSRRepository extends JpaRepository<CSR, Long> {

    @Query("select c from CSR c where c.verified = true")
    List<CSR> findAllVerified();

    CSR findOneById(Long id);

    CSR findByEmail(String email);

    @Modifying
    @Transactional
    void delete(CSR csr);

    Optional<CSR> findBySecurityCode(String secureCode);

    Optional<CSR> findBySecurityCodeAndVerified(String secureCode, boolean verified);
}