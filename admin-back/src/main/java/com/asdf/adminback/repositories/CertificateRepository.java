package com.asdf.adminback.repositories;

import com.asdf.adminback.models.RCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<RCertificate, Long> {

    RCertificate getByAlias(String alias);

    RCertificate findByAlias(String alias);
}
