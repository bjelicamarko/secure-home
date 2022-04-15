package com.asdf.adminback.services;

import com.asdf.adminback.dto.CertificateDataDTO;
import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.dto.ExtendedKeyUsageDTO;
import com.asdf.adminback.dto.KeyUsageDTO;
import com.asdf.adminback.models.IssuerData;
import com.asdf.adminback.models.SubjectData;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static com.asdf.adminback.util.Constants.*;

@Service
public class CertificateServiceImpl implements CertificateService{

    @Autowired
    private KeyStoreService keyStoreService;

    @PostConstruct
    private void postConstruct(){
        Security.addProvider(new BouncyCastleProvider());

        // Ovo prilikom prvog pokretanja da se izgenerisu ROOT i mediator
        // createAndWriteRootCertificate();
        // createAndWriteIntermediateCertificate();
    }

    @Override
    public X509Certificate createNewCertificate(SubjectData subjectData, IssuerData issuerData,
                                                 PublicKey issuerPublicKey) {

        X509Certificate cert = generateCertificate(subjectData, issuerData);

        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        assert cert != null;
        System.out.println(cert.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(cert.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(cert);
        System.out.println("-------------------------------------------------------");

        try {
            cert.verify(issuerPublicKey);
            System.out.println("\nValidacija uspesna :)");
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
            System.out.println("\nValidacija neuspesna :(");
            e.printStackTrace();
        }

        return cert;
    }

    @Override
    public void createAndWriteRootCertificate() {
        KeyPair keyPairRoot = keyStoreService.generateKeyPair();
        X500Name x500Name = generateRootX500Name();
        IssuerData issuerData = generateIssuerDataFromX500Name(x500Name, keyPairRoot.getPrivate());
        SubjectData subjectData = generateSubjectDataFromX500Name(x500Name, keyPairRoot, "2022-01-01",
                "2027-12-31", "1");

        Certificate cert = createNewCertificate(subjectData, issuerData, keyPairRoot.getPublic());


        keyStoreService.loadKeyStore(FILE_PATH, PWD.toCharArray());
        keyStoreService.write("root", issuerData.getPrivateKey(), "root".toCharArray(), cert);
        keyStoreService.saveKeyStore(FILE_PATH, PWD.toCharArray());
    }

    @Override
    public void createAndWriteIntermediateCertificate() {
        IssuerData issuerData = keyStoreService.readIssuerFromStore(FILE_PATH, "root", PWD.toCharArray(), "root".toCharArray());
        X509Certificate cert = (X509Certificate) keyStoreService.readCertificate(FILE_PATH, PWD, "root");
        KeyPair keyPairIssuer = new KeyPair(cert.getPublicKey(), issuerData.getPrivateKey());

        KeyPair keyPairSubject = keyStoreService.generateKeyPair();
        X500Name x500NameSubject = generateIntermediateX500Name();
        SubjectData subjectData = generateSubjectDataFromX500Name(x500NameSubject, keyPairSubject, "2022-01-02", "2025-12-31", "2");
        X509Certificate subjectCert = createNewCertificate(subjectData, issuerData, keyPairIssuer.getPublic());

        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(subjectCert.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(subjectCert.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(subjectCert);
        System.out.println("-------------------------------------------------------");

        try {
            subjectCert.verify(keyPairIssuer.getPublic());
            System.out.println("\nValidacija uspesna :)");
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
            System.out.println("\nValidacija neuspesna :(");
            e.printStackTrace();
        }

        keyStoreService.loadKeyStore(FILE_PATH, PWD.toCharArray());
        keyStoreService.write("intermediate", keyPairSubject.getPrivate(), "intermediate".toCharArray(), subjectCert);
        keyStoreService.saveKeyStore(FILE_PATH, PWD.toCharArray());
    }


    /** Deo koda za kreiranje leaf sertifikata ----------------------------------------------------------------------- **/

    @Override
    public void createAndWriteLeafCertificate(CertificateSigningDTO certificateSigningDTO) {
        IssuerData issuerData = keyStoreService.readIssuerFromStore(FILE_PATH, "intermediate", PWD.toCharArray(), "intermediate".toCharArray());
        X509Certificate cert = (X509Certificate) keyStoreService.readCertificate(FILE_PATH, PWD, "intermediate");
        KeyPair keyPairIssuer = new KeyPair(cert.getPublicKey(), issuerData.getPrivateKey());

        KeyPair keyPairSubject = keyStoreService.generateKeyPair();
        X500Name x500NameSubject = generateX500Name(certificateSigningDTO.getCertificateDataDTO());
        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().plusYears(1).toString();
        String sn = keyStoreService.generateNextSerialNumber().toString();
        SubjectData subjectData = generateSubjectDataFromX500Name(x500NameSubject, keyPairSubject, startDate, endDate, sn);

        X509Certificate subjectCert = createNewLeafCertificate(subjectData, issuerData, keyPairIssuer.getPublic(), certificateSigningDTO);

        keyStoreService.loadKeyStore(FILE_PATH, PWD.toCharArray());
        String alias = certificateSigningDTO.getCertificateDataDTO().getEmail();
        keyStoreService.write(alias, keyPairSubject.getPrivate(), alias.toCharArray(), subjectCert);
        keyStoreService.saveKeyStore(FILE_PATH, PWD.toCharArray());
    }

    private X500Name generateX500Name(CertificateDataDTO certificateDataDTO) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.E, certificateDataDTO.getEmail());
        builder.addRDN(BCStyle.CN, certificateDataDTO.getCommonName());
        builder.addRDN(BCStyle.O, certificateDataDTO.getOrganization());
        builder.addRDN(BCStyle.OU, certificateDataDTO.getOrganizationUnit());
        builder.addRDN(BCStyle.ST, certificateDataDTO.getState());
        builder.addRDN(BCStyle.C, certificateDataDTO.getCountry());
        builder.addRDN(BCStyle.UID, certificateDataDTO.getEmail());

        return builder.build();
    }

    private SubjectData generateSubjectDataFromX500Name(X500Name x500Name, KeyPair keyPairSubject,
                                                        String startDate, String endDate, String sn) {
        try {
            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date start = iso8601Formater.parse(startDate);
            Date end = iso8601Formater.parse(endDate);
            return new SubjectData(keyPairSubject.getPublic(), x500Name, sn, start, end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X509Certificate createNewLeafCertificate(SubjectData subjectData, IssuerData issuerData,
                                                     PublicKey issuerPublicKey, CertificateSigningDTO certificateSigningDTO) {
        X509Certificate cert = null;
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");
            // System.out.println(builder);
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            addExtensions(certGen, certificateSigningDTO, subjectData.getPublicKey(), issuerPublicKey);
            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            cert = certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }

    private void addExtensions(X509v3CertificateBuilder certGen, CertificateSigningDTO certificateSigningDTO, PublicKey subjectPublicKey, PublicKey issuerPublicKey) {
        try {
            JcaX509ExtensionUtils certExtUtils = new JcaX509ExtensionUtils();
            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(certificateSigningDTO.isCa()));
            certGen.addExtension(Extension.authorityKeyIdentifier, false, certExtUtils.createAuthorityKeyIdentifier(issuerPublicKey));
            certGen.addExtension(Extension.subjectKeyIdentifier, false, certExtUtils.createAuthorityKeyIdentifier(subjectPublicKey));

            addKeyUsageExtensions(certGen, certificateSigningDTO.getKeyUsageDTO());
            addExtendedKeyUsageExtensions(certGen, certificateSigningDTO.getExtendedKeyUsageDTO());
        } catch (NoSuchAlgorithmException | CertIOException e) {
            e.printStackTrace();
        }
    }

    private void addKeyUsageExtensions(X509v3CertificateBuilder certGen, KeyUsageDTO keyUsageDTO) throws CertIOException {
        if(keyUsageDTO == null) return;

        if(keyUsageDTO.isCertificateSigning()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyCertSign));
        if(keyUsageDTO.isCrlSign()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.cRLSign));
        if(keyUsageDTO.isDataEncipherment()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.dataEncipherment));
        if(keyUsageDTO.isDecipherOnly()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.decipherOnly));
        if(keyUsageDTO.isDigitalSignature()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.digitalSignature));
        if(keyUsageDTO.isEncipherOnly()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.encipherOnly));
        if(keyUsageDTO.isKeyAgreement()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyAgreement));
        if(keyUsageDTO.isKeyEncipherment()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyEncipherment));
        if(keyUsageDTO.isNonRepudiation()) certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.nonRepudiation));
    }

    private void addExtendedKeyUsageExtensions(X509v3CertificateBuilder certGen, ExtendedKeyUsageDTO extendedKeyUsageDTO) throws CertIOException {
        if(extendedKeyUsageDTO == null) return;

        // if(extendedKeyUsageDTO.isAdobePdfSigning()) certGen.addExtension(Extension.extendedKeyUsage, false, null);
        // if(extendedKeyUsageDTO.isDocumentSigning()) certGen.addExtension(Extension.extendedKeyUsage, false, null);
        if(extendedKeyUsageDTO.isEmailProtection()) certGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_emailProtection));
        if(extendedKeyUsageDTO.isIpSecurityEndSystem()) certGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_ipsecEndSystem));
        if(extendedKeyUsageDTO.isOcspSigning()) certGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_OCSPSigning));
        if(extendedKeyUsageDTO.isTimeStamping()) certGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_timeStamping));
        if(extendedKeyUsageDTO.isTlsWebClientAuthentication()) certGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));
        if(extendedKeyUsageDTO.isTlsWebServerAuthentication()) certGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
        // if(extendedKeyUsageDTO.isTslSigning()) certGen.addExtension(Extension.extendedKeyUsage, false, null);
    }

    /** Deo koda za kreiranje leaf sertifikata ----------------------------------------------------------------------- **/

    private IssuerData generateIssuerDataFromX500Name(X500Name x500Name, PrivateKey issuerKey) {
        return new IssuerData(issuerKey, x500Name);
    }

    private X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData) {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");
            System.out.println(builder);
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            return certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X500Name generateRootX500Name() {
        // Dodati ekstenzije za CA
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Tim asf");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "asf@maildrop.cc");
        builder.addRDN(BCStyle.UID, "1");
        return builder.build();
    }

    private X500Name generateIntermediateX500Name() {
        // Dodati ekstenziju za CA
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Adminko Adminic");
        builder.addRDN(BCStyle.SURNAME, "Adminic"); // Skloniti
        builder.addRDN(BCStyle.GIVENNAME, "Adminko"); // Skloniti
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "adminko@maildrop.cc");
        builder.addRDN(BCStyle.UID, "2"); // Automatski generisati
        // Dodati state polje BCStyle.ST
        return builder.build();
    }


}
