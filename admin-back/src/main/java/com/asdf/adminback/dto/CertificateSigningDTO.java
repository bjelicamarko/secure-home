package com.asdf.adminback.dto;

public class CertificateSigningDTO {

    private CertificateDataDTO certificateDataDTO;
    private KeyUsageDTO keyUsageDTO;
    private ExtendedKeyUsageDTO extendedKeyUsageDTO;
    private boolean isCa;

    public CertificateSigningDTO() { }

    public CertificateDataDTO getCertificateDataDTO() {
        return certificateDataDTO;
    }

    public void setCertificateDataDTO(CertificateDataDTO certificateDataDTO) {
        this.certificateDataDTO = certificateDataDTO;
    }

    public KeyUsageDTO getKeyUsageDTO() {
        return keyUsageDTO;
    }

    public void setKeyUsageDTO(KeyUsageDTO keyUsageDTO) {
        this.keyUsageDTO = keyUsageDTO;
    }

    public ExtendedKeyUsageDTO getExtendedKeyUsageDTO() {
        return extendedKeyUsageDTO;
    }

    public void setExtendedKeyUsageDTO(ExtendedKeyUsageDTO extendedKeyUsageDTO) {
        this.extendedKeyUsageDTO = extendedKeyUsageDTO;
    }

    public boolean isCa() {
        return isCa;
    }

    public void setCa(boolean ca) {
        isCa = ca;
    }
}
