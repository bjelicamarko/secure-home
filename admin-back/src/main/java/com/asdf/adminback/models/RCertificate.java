package com.asdf.adminback.models;

import javax.persistence.*;

@Entity
public class RCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String alias;

    @Column(nullable = false, unique = true)
    private String revocationReason;

    @Column(nullable = false)
    private String revocationDate;

    public RCertificate() {}

    public RCertificate(String alias, String revocationReason, String revocationDate){
        this.alias = alias;
        this.revocationReason = revocationReason;
        this.revocationDate = revocationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String serialNumber) {
        this.alias = serialNumber;
    }

    public String getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(String revocationReason) {
        this.revocationReason = revocationReason;
    }

    public String getRevocationDate() {
        return revocationDate;
    }

    public void setRevocationDate(String revocationDate) {
        this.revocationDate = revocationDate;
    }
}
