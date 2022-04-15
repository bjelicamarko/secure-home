package com.asdf.adminback.models;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;

import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CertificateDTO {
    private String issuedTo;
    private String issuedBy;
    private String validFrom;
    private String validTo;
    private String serialNumber;
    private String complexNameSubject;
    private String complexNameIssuer;
    private String publicKeyAlgorithm;

    public CertificateDTO() {

    }

    public CertificateDTO(X509Certificate certificate)  {
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
}
