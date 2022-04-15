package com.asdf.adminback.services;

import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.exceptions.CertificateSigningDTOException;
import com.asdf.adminback.models.IssuerData;
import com.asdf.adminback.models.SubjectData;

import java.security.PublicKey;
import java.security.cert.X509Certificate;

public interface CertificateService {

    X509Certificate createNewCertificate(SubjectData subjectData, IssuerData issuerData, PublicKey issuerPublicKey);

    void createAndWriteRootCertificate();

    void createAndWriteIntermediateCertificate();

    void createAndWriteLeafCertificate(CertificateSigningDTO certificateSigningDTO) throws CertificateSigningDTOException;
}
