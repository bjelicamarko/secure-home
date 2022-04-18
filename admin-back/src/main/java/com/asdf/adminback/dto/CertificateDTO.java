package com.asdf.adminback.dto;

import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.util.encoders.Hex;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CertificateDTO {
    private String issuedTo;
    private String issuedBy;
    private String validFrom;
    private String validTo;
    private String serialNumber;
    private String complexNameSubject;
    private String complexNameIssuer;
    private String publicKeyAlgorithm;
    private List<String> keyUsages;
    private List<String> extendedKeyUsages;
    private int isCA;
    private int version;
    private String authorityKeyIdentifier;
    private String subjectKeyIdentifier;

    public CertificateDTO() {
        this.keyUsages = new ArrayList<>();
        this.extendedKeyUsages = new ArrayList<>();
    }

    public CertificateDTO(X509Certificate certificate) throws CertificateParsingException {
        this();
        //this.issuedTo = certificate.getSubjectX500Principal().getName();
        X500Name x500name = new X500Name(certificate.getSubjectX500Principal().getName());
        RDN cn = x500name.getRDNs(BCStyle.CN)[0];
        this.issuedTo = IETFUtils.valueToString(cn.getFirst().getValue());
        this.complexNameSubject = "CN=" + IETFUtils.valueToString(cn.getFirst().getValue()) + " & ";
        cn = x500name.getRDNs(BCStyle.O)[0];
        this.complexNameSubject = this.complexNameSubject + "O=" + IETFUtils.valueToString(cn.getFirst().getValue()) + " & ";
        cn = x500name.getRDNs(BCStyle.OU)[0];
        this.complexNameSubject = this.complexNameSubject + "OU=" + IETFUtils.valueToString(cn.getFirst().getValue());

        x500name = new X500Name(certificate.getIssuerX500Principal().getName());
        cn = x500name.getRDNs(BCStyle.CN)[0];
        this.issuedBy = IETFUtils.valueToString(cn.getFirst().getValue());
        this.complexNameIssuer = "CN=" + IETFUtils.valueToString(cn.getFirst().getValue()) + " & ";
        cn = x500name.getRDNs(BCStyle.O)[0];
        this.complexNameIssuer = this.complexNameIssuer + "O=" + IETFUtils.valueToString(cn.getFirst().getValue()) + " & ";
        cn = x500name.getRDNs(BCStyle.OU)[0];
        this.complexNameIssuer = this.complexNameIssuer + "OU=" + IETFUtils.valueToString(cn.getFirst().getValue());

        //MM/dd/yyyy
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        this.validFrom = df.format(certificate.getNotBefore());
        this.validTo = df.format(certificate.getNotAfter());

        this.serialNumber = certificate.getSerialNumber().toString();
        this.publicKeyAlgorithm = certificate.getPublicKey().getAlgorithm();

        if (certificate.getKeyUsage() != null)
            this.setKeyUsages(certificate.getKeyUsage());

        if (certificate.getExtendedKeyUsage() != null)
            this.setExtendedKeyUsages(certificate.getExtendedKeyUsage());

        this.isCA = certificate.getBasicConstraints();
        this.version = certificate.getVersion();

        byte[] extensionValue = certificate.getExtensionValue("2.5.29.35");
        byte[] octets = DEROctetString.getInstance(extensionValue).getOctets();
        AuthorityKeyIdentifier authorityKeyIdentifier = AuthorityKeyIdentifier.getInstance(octets);
        this.authorityKeyIdentifier = getKeyIdentifier(authorityKeyIdentifier.getKeyIdentifier());

        extensionValue = certificate.getExtensionValue("2.5.29.14");
        octets = DEROctetString.getInstance(extensionValue).getOctets();
        SubjectKeyIdentifier subjectKeyIdentifier = SubjectKeyIdentifier.getInstance(octets);
        this.subjectKeyIdentifier = getKeyIdentifier(subjectKeyIdentifier.getKeyIdentifier());
    }

    private String getKeyIdentifier(byte[] keyIdentifier) {
        String keyIdentifierHex = new String(Hex.encode(keyIdentifier));
        String uppercase = keyIdentifierHex.toUpperCase(Locale.ROOT);
        StringBuilder sb = new StringBuilder();
        sb.append("0x"+uppercase.charAt(0));
        for (int i = 1; i < uppercase.length(); i++){
            if (i % 4 == 0) sb.append(" ");
            sb.append(uppercase.charAt(i));
        }
        return sb.toString();
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setComplexNameSubject(String complexNameSubject) {
        this.complexNameSubject = complexNameSubject;
    }

    public void setComplexNameIssuer(String complexNameIssuer) {
        this.complexNameIssuer = complexNameIssuer;
    }

    public String getComplexNameSubject() {
        return complexNameSubject;
    }

    public String getComplexNameIssuer() {
        return complexNameIssuer;
    }

    public String getPublicKeyAlgorithm() {
        return publicKeyAlgorithm;
    }

    public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
        this.publicKeyAlgorithm = publicKeyAlgorithm;
    }

    public List<String> getKeyUsages() {
        return keyUsages;
    }

    public void setKeyUsages(List<String> keyUsages) {
        this.keyUsages = keyUsages;
    }

    public void setKeyUsages(boolean[] list){
        this.keyUsages = new ArrayList<>();
        if (list[0])
            this.keyUsages.add("Digital Signature");
        if (list[1])
            this.keyUsages.add("Non Repudiation");
        if (list[2])
            this.keyUsages.add("Key Encipherment");
        if (list[3])
            this.keyUsages.add("Data Encipherment");
        if (list[4])
            this.keyUsages.add("Key Agreement");
        if (list[5])
            this.keyUsages.add("Key Cert Sign");
        if (list[6])
            this.keyUsages.add("cRL Sign");
        if (list[7])
            this.keyUsages.add("Encipher Only");
        if (list[8])
            this.keyUsages.add("Decipher Only");
    }

    public List<String> getExtendedKeyUsages() {
        return extendedKeyUsages;
    }

    public void setExtendedKeyUsages(List<String> extendedKeyUsagesCodes) {
        this.extendedKeyUsages = new ArrayList<>();
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.1"))
            this.extendedKeyUsages.add("TLS Web Server Authentication: 1.3.6.1.5.5.7.3.1");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.2"))
            this.extendedKeyUsages.add("TLS Web Client Authentication: 1.3.6.1.5.5.7.3.2");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.3"))
            this.extendedKeyUsages.add("Code signing: 1.3.6.1.5.5.7.3.3");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.4.1.311.10.3.12"))
            this.extendedKeyUsages.add("Document signing: 1.3.6.1.4.1.311.10.3.12");
        if (extendedKeyUsagesCodes.contains("1.2.840.113583.1.1.5"))
            this.extendedKeyUsages.add("Adobe PDF Signing: 1.2.840.113583.1.1.5");
        if (extendedKeyUsagesCodes.contains("0.4.0.2231.3.0"))
            this.extendedKeyUsages.add("TSL Signing: 0.4.0.2231.3.0");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.4"))
            this.extendedKeyUsages.add("E-mail Protection: 1.3.6.1.5.5.7.3.4");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.4.1.311.10.3.4"))
            this.extendedKeyUsages.add("Encrypted File System: 1.3.6.1.4.1.311.10.3.4");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.5"))
            this.extendedKeyUsages.add("Ip Security End System: 1.3.6.1.5.5.7.3.5");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.6"))
            this.extendedKeyUsages.add("Ip Security Tunnel Termination: 1.3.6.1.5.5.7.3.6");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.7"))
            this.extendedKeyUsages.add("Ip Security User: 1.3.6.1.5.5.7.3.7");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.8"))
            this.extendedKeyUsages.add("Time Stamping: 1.3.6.1.5.5.7.3.8");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.9"))
            this.extendedKeyUsages.add("OCSP Signing: 1.3.6.1.5.5.7.3.9");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.4.1.311.20.2.2"))
            this.extendedKeyUsages.add("Smartcard Logon: 1.3.6.1.4.1.311.20.2.2");
        if (extendedKeyUsagesCodes.contains("2.5.29.37.0"))
            this.extendedKeyUsages.add("Any Extended Key Usage: 2.5.29.37.0");
    }

    public int getIsCA() {
        return isCA;
    }

    public void setIsCA(int isCA) {
        this.isCA = isCA;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAuthorityKeyIdentifier() {
        return authorityKeyIdentifier;
    }

    public void setAuthorityKeyIdentifier(String authorityKeyIdentifier) {
        this.authorityKeyIdentifier = authorityKeyIdentifier;
    }

    public String getSubjectKeyIdentifier() {
        return subjectKeyIdentifier;
    }

    public void setSubjectKeyIdentifier(String subjectKeyIdentifier) {
        this.subjectKeyIdentifier = subjectKeyIdentifier;
    }
}
