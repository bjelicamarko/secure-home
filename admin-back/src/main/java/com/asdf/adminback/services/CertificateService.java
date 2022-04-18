package com.asdf.adminback.services;

import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.exceptions.CertificateNotFound;
import com.asdf.adminback.exceptions.CertificateSigningDTOException;
import com.asdf.adminback.exceptions.InvalidCertificate;
import com.asdf.adminback.models.IssuerData;
import com.asdf.adminback.models.SubjectData;

import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public interface CertificateService {

    X509Certificate createNewCertificate(SubjectData subjectData, IssuerData issuerData, PublicKey issuerPublicKey);

    void createAndWriteRootCertificate();

    void createAndWriteIntermediateCertificate();

    void createAndWriteLeafCertificate(CertificateSigningDTO certificateSigningDTO) throws CertificateSigningDTOException;

    void revokeCertificate(String alias, String reason) throws CertificateNotFound;

    void validateCertificate(String alias) throws CertificateNotFound, InvalidCertificate, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException;
}
