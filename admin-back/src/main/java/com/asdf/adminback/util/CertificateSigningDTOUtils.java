package com.asdf.adminback.util;

import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.exceptions.CertificateSigningDTOException;

import java.util.regex.Pattern;

public class CertificateSigningDTOUtils {

    public static void checkBasicCertificateInfo(CertificateSigningDTO csDTO) throws CertificateSigningDTOException {
        if(csDTO == null) {
            throw new CertificateSigningDTOException("Invalid certificate signing dto!");
        }

        String email = csDTO.getCertificateDataDTO().getEmail();
        if(email.equals("") || !isEmailValid(email)) {
            throw new CertificateSigningDTOException("Invalid email address!");
        }

        String commonName = csDTO.getCertificateDataDTO().getCommonName();
        if(commonName == null || commonName.length() < 3) {
            throw new CertificateSigningDTOException("Invalid common name field. Should have minimum 3 characters.");
        }

        String organization = csDTO.getCertificateDataDTO().getOrganization();
        if(organization == null || organization.length() < 3) {
            throw new CertificateSigningDTOException("Invalid organization field! Should have minimum 3 characters.");
        }

        String organizationUnit = csDTO.getCertificateDataDTO().getOrganizationUnit();
        if(organizationUnit == null || organizationUnit.length() < 3) {
            throw new CertificateSigningDTOException("Invalid organization unit field! Should have minimum 3 characters.");
        }

        String state = csDTO.getCertificateDataDTO().getState();
        if(state == null || state.length() < 3) {
            throw new CertificateSigningDTOException("Invalid state field! Should have minimum 3 characters.");
        }

        String country = csDTO.getCertificateDataDTO().getCountry();
        if(country == null || country.length() < 3) {
            throw new CertificateSigningDTOException("Invalid country field! Should have minimum 3 characters.");
        }
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
