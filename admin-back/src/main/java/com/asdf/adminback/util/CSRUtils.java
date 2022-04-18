package com.asdf.adminback.util;

import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;

public class CSRUtils {

    public static void checkBasicCSRInfo(CSR csr) throws CSRException {
        if(csr == null) {
            throw new CSRException("Invalid csr!");
        }
        else if(csr.getEmail().equals("") || !CertificateSigningDTOUtils.isEmailValid(csr.getEmail())) {
            throw new CSRException("Invalid email sent in csr!");
        }
        else if(csr.getCommonName().equals("") || csr.getCommonName() == null || csr.getCommonName().length() < 3) {
            throw new CSRException("Common name field must have at least 3 characters.");
        }
        else if(csr.getOrganizationUnit().equals("") || csr.getOrganizationUnit() == null || csr.getOrganizationUnit().length() < 3) {
            throw new CSRException("Organization unit field must have at least 3 characters.");
        }
        else if(csr.getOrganization().equals("") || csr.getOrganization() == null || csr.getOrganization().length() < 3) {
            throw new CSRException("Organization field must have at least 3 characters.");
        }
        else if(csr.getCity().equals("") || csr.getCity() == null || csr.getCity().length() < 3) {
            throw new CSRException("City field must have at least 3 characters.");
        }
        else if(csr.getState().equals("") || csr.getState() == null || csr.getState().length() < 3) {
            throw new CSRException("State field must have at least 3 characters.");
        }
        else if(csr.getCountry().equals("") || csr.getCountry() == null || csr.getCountry().length() != 2) {
            throw new CSRException("Country field must have 2 characters.");
        }
    }
}
