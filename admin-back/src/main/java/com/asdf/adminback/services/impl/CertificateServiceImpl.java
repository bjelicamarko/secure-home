package com.asdf.adminback.services.impl;

import com.asdf.adminback.dto.*;
import com.asdf.adminback.exceptions.CertificateNotFound;
import com.asdf.adminback.exceptions.CertificateSigningDTOException;
import com.asdf.adminback.exceptions.InvalidCertificate;
import com.asdf.adminback.models.IssuerData;
import com.asdf.adminback.models.RCertificate;
import com.asdf.adminback.models.SubjectData;
import com.asdf.adminback.repositories.CertificateRepository;
import com.asdf.adminback.services.CSRService;
import com.asdf.adminback.services.CertificateService;
import com.asdf.adminback.services.KeyStoreService;
import com.asdf.adminback.util.CertificateSigningDTOUtils;
import org.apache.tomcat.jni.Local;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.asdf.adminback.util.Constants.*;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private CSRService csrService;

    @Autowired
    private CertificateRepository certificateRepository;

    @PostConstruct
    private void postConstruct(){
        Security.addProvider(new BouncyCastleProvider());

        // Ovo prilikom prvog pokretanja da se izgenerisu ROOT i mediator
        // createAndWriteRootCertificate();
        // createAndWriteIntermediateCertificate();
    }

    /** Deo koda za kreiranje leaf sertifikata ----------------------------------------------------------------------- **/

    @Override
    public void createAndWriteLeafCertificate(CertificateSigningDTO certificateSigningDTO) throws CertificateSigningDTOException {
        if (csrService.findByEmail(certificateSigningDTO.getCertificateDataDTO().getEmail()) == null)
            throw new CertificateSigningDTOException("There is no CSR for given email!");
        if (keyStoreService.containsAlias(certificateSigningDTO.getCertificateDataDTO().getEmail()))
            throw new CertificateSigningDTOException("Alias already exists!");
        CertificateSigningDTOUtils.checkBasicCertificateInfo(certificateSigningDTO);

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

        // Brisanje csr-a iz baze
        csrService.delete(csrService.findByEmail(certificateSigningDTO.getCertificateDataDTO().getEmail()));
    }

    @Override
    public void revokeCertificate(String alias, String reason) throws Exception {
        if (!keyStoreService.containsAlias(alias)) throw new CertificateNotFound(alias);

        RCertificate certificate = certificateRepository.findByAlias(alias);
        if (certificate != null) throw new Exception(String.format("Certificate '%s' is already invoked.", alias));

        RCertificate rCertificate = new RCertificate(alias, reason, LocalDate.now().toString());
        certificateRepository.save(rCertificate);
    }

    @Override
    public void validateCertificate(String alias) throws CertificateNotFound, InvalidCertificate, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        if (!keyStoreService.containsAlias(alias)) throw new CertificateNotFound(alias);
        if(alias.equals("root")) validateRootCertificate(alias);
        else if(alias.equals("intermediate")) validateIntermediateCertificate(alias);
        else validateLeafCertificate(alias);
    }

    private void validateLeafCertificate(String alias) throws InvalidCertificate, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        validateRootCertificate("root");
        PublicKey intermediatePKey = validateIntermediateCertificate("intermediate");
        X509Certificate leafCert = (X509Certificate) keyStoreService.readCertificate(FILE_PATH, PWD, alias);
        validateCertificate(leafCert, intermediatePKey, alias);
    }

    private PublicKey validateIntermediateCertificate(String alias) throws InvalidCertificate, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        PublicKey rootPKey = validateRootCertificate("root");
        X509Certificate intermediateCert = (X509Certificate) keyStoreService.readCertificate(FILE_PATH, PWD, alias);
        validateCertificate(intermediateCert, rootPKey, alias);
        return intermediateCert.getPublicKey();
    }

    private PublicKey validateRootCertificate(String alias) throws InvalidCertificate, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        X509Certificate rootCert = (X509Certificate) keyStoreService.readCertificate(FILE_PATH, PWD, alias);
        validateCertificate(rootCert, rootCert.getPublicKey(), alias);
        return rootCert.getPublicKey();
    }

    private void validateCertificate(X509Certificate certificate, PublicKey publicKey, String alias) throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificate {
        // Is it revoked?
        RCertificate rCertificate = certificateRepository.getByAlias(alias);
        if(rCertificate != null)
            throw new InvalidCertificate(alias);
        // Is it expired?
        // Date to LocalDate
        LocalDate validFrom = certificate.getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate validTo = certificate.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        if(now.isBefore(validFrom) || now.isAfter(validTo))
            throw new InvalidCertificate(alias);
        // Is dig. signature valid?
        certificate.verify(publicKey);
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
            certGen.addExtension(Extension.subjectKeyIdentifier, false, certExtUtils.createSubjectKeyIdentifier(subjectPublicKey));

            addKeyUsageExtensions(certGen, certificateSigningDTO.getKeyUsageDTO());
            addExtendedKeyUsageExtensions(certGen, certificateSigningDTO.getExtendedKeyUsageDTO());
        } catch (NoSuchAlgorithmException | CertIOException e) {
            e.printStackTrace();
        }
    }

    private void addKeyUsageExtensions(X509v3CertificateBuilder certGen, KeyUsageDTO keyUsageDTO) throws CertIOException {
        if(keyUsageDTO == null) return;
        int res = 0;

        if(keyUsageDTO.isCertificateSigning()) res = res | KeyUsage.keyCertSign;
        if(keyUsageDTO.isCrlSign()) res = res | KeyUsage.cRLSign;
        if(keyUsageDTO.isDataEncipherment()) res = res | KeyUsage.dataEncipherment;
        if(keyUsageDTO.isDecipherOnly()) res = res | KeyUsage.decipherOnly;
        if(keyUsageDTO.isDigitalSignature()) res = res | KeyUsage.digitalSignature;
        if(keyUsageDTO.isEncipherOnly()) res = res | KeyUsage.encipherOnly;
        if(keyUsageDTO.isKeyAgreement()) res = res | KeyUsage.keyAgreement;
        if(keyUsageDTO.isKeyEncipherment()) res = res | KeyUsage.keyEncipherment;
        if(keyUsageDTO.isNonRepudiation()) res = res | KeyUsage.nonRepudiation;

        certGen.addExtension(Extension.keyUsage, false, new KeyUsage(res));
    }

    private void addExtendedKeyUsageExtensions(X509v3CertificateBuilder certGen, ExtendedKeyUsageDTO extendedKeyUsageDTO) throws CertIOException {
        if(extendedKeyUsageDTO == null) return;

        List<KeyPurposeId> usages = new ArrayList<>();
        if(extendedKeyUsageDTO.isEmailProtection()) usages.add(KeyPurposeId.id_kp_emailProtection);
        if(extendedKeyUsageDTO.isIpSecurityEndSystem()) usages.add(KeyPurposeId.id_kp_ipsecEndSystem);
        if(extendedKeyUsageDTO.isOcspSigning()) usages.add(KeyPurposeId.id_kp_OCSPSigning);
        if(extendedKeyUsageDTO.isTimeStamping()) usages.add(KeyPurposeId.id_kp_timeStamping);
        if(extendedKeyUsageDTO.isTlsWebClientAuthentication()) usages.add(KeyPurposeId.id_kp_clientAuth);
        if(extendedKeyUsageDTO.isTlsWebServerAuthentication()) usages.add(KeyPurposeId.id_kp_serverAuth);

        KeyPurposeId[] usagesArray = new KeyPurposeId[usages.size()];
        for(int i = 0; i < usages.size(); i++) {
            usagesArray[i] = usages.get(i);
        }
        certGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(usagesArray));

        // if(extendedKeyUsageDTO.isAdobePdfSigning()) certGen.addExtension(Extension.extendedKeyUsage, false, null);   invalid
        // if(extendedKeyUsageDTO.isDocumentSigning()) certGen.addExtension(Extension.extendedKeyUsage, false, null);   invalid
        // if(extendedKeyUsageDTO.isTslSigning()) certGen.addExtension(Extension.extendedKeyUsage, false, null);        invalid
    }

    /** Deo koda za kreiranje leaf sertifikata iznad ------------------------------------------------------------------ **/

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

    @Override
    public X509Certificate createNewCertificate(SubjectData subjectData, IssuerData issuerData,
                                                PublicKey issuerPublicKey) {

        X509Certificate cert = generateCertificate(subjectData, issuerData, issuerPublicKey);

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


    private IssuerData generateIssuerDataFromX500Name(X500Name x500Name, PrivateKey issuerKey) {
        return new IssuerData(issuerKey, x500Name);
    }

    private X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, PublicKey issuerPublicKey) {
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

            JcaX509ExtensionUtils certExtUtils = new JcaX509ExtensionUtils();
            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
            certGen.addExtension(Extension.authorityKeyIdentifier, false, certExtUtils.createAuthorityKeyIdentifier(issuerPublicKey));
            certGen.addExtension(Extension.subjectKeyIdentifier, false, certExtUtils.createSubjectKeyIdentifier(subjectData.getPublicKey()));

            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            return certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException | CertIOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X500Name generateRootX500Name() {
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
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Admin");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "admin_asf@maildrop.cc");
        builder.addRDN(BCStyle.UID, "2");

        return builder.build();
    }


}
