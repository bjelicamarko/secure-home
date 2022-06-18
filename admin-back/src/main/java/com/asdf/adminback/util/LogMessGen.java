package com.asdf.adminback.util;

import com.asdf.adminback.dto.CertificateDataDTO;
import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.dto.RevokedCertificateDTO;
import com.asdf.adminback.models.CSR;

public class LogMessGen {

    public static String successfulLogin(String username) {
        return String.format("User with username: '%s' successfully logged in", username);
    }

    public static String badCredentialLogin(String username) {
        return String.format("Login failed with bad credentials for username: '%s'", username);
    }

    public static String userNotFoundLogin(String username) {
        return String.format("Login failed with non-existent username: '%s'", username);
    }

    public static String accountLockedLogin(String username) {
        return String.format("Login failed for locked account for username: '%s'", username);
    }

    public static String accountUnverifiedLogin(String username) {
        return String.format("Login failed for unverified account for username: '%s'", username);
    }

    public static String successfulLogout(String username) {
        return String.format("User with username: '%s' successfully logged out", username);
    }

//    public static String successfulPathOperation(String username, String urlPath) {
//        return String.format("User with username: '%s' successfully finished operation on url path: '%s'", username, urlPath);
//    }

    public static String successfulDeleteUser(String username, String userToDelete) {
        return String.format("User with username: '%s' successfully deleted user with username: '%s'", username, userToDelete);
    }

    public static String successfulUnlockUser(String username, String userToDelete) {
        return String.format("User with username: '%s' successfully unlocked user with username: '%s'", username, userToDelete);
    }

    public static String successfulRegistration(String username) {
        return String.format("User with username: '%s' successfully registered", username);
    }

    public static String successfulAccVerification(String username) {
        return String.format("Account with username: '%s' successfully verified", username);
    }

    public static String exMessUser(String username, String exceptionMessage) {
        return String.format("Account with username: '%s' triggered exception with message: '%s'", username, exceptionMessage);
    }

    public static String exMessRegistration(String username, String exceptionMessage) {
        return String.format("While doing registration for username: '%s' exception is triggered with message: '%s'", username, exceptionMessage);
    }

    public static String internalServerError(String username) {
        return String.format("User with username: '%s' triggered an unknown error", username);
    }

    public static String internalServerError() {
        return "Unknown error happened";
    }

    public static String badRequestError(String username) {
        return String.format("User with username: '%s' triggered an bad request error", username);
    }

    public static String badRequestError() {
        return "Bad request error happened";
    }

    public static String successfulRealEstateCreation(String username, String realEstateName) {
        return String.format("User with username: '%s' successfully added new real estate with name '%s'", username, realEstateName);
    }

    public static String successfulRealEstateUpdate(String username, String realEstateName) {
        return String.format("User with username: '%s' successfully updated real estate with name '%s'", username, realEstateName);
    }

    public static String successfulUserRealEstateCreation(String username, String user, String realEstateName, String role) {
        return String.format("User with username: '%s' successfully added new user real estate triplet (%s, %s, %s)", username, user, realEstateName, role);
    }

    public static String successfulUserRealEstateRoleChange(String username, String user, String realEstateName, String role) {
        return String.format("User with username: '%s' successfully changed role in user real estate (%s, %s) to '%s'", username, user, realEstateName, role);
    }

    public static String successfulUserRealEstateDelete(String username, String user, String realEstateName) {
        return String.format("User with username: '%s' successfully deleted user real estate relation (%s, %s)", username, user, realEstateName);
    }

    public static String userNotInRealEstate(String username, String realEstateName) {
        return String.format("User with username: '%s' is not owner in '%s'", username, realEstateName);
    }

    public static String successfulUserNotificationSeen(String username, Long anId) {
        return String.format("User with username: '%s' seen notification with id: '%s'", username, anId);
    }

    public static String maliciousIpAddress(String remoteAddress) {
        return String.format("Spring application was hit from malicious address '%s'", remoteAddress);
    }

    public static String expiredJWT(String username, String urlPath) {
        return String.format("User with username: '%s' tried to access url endpoint: " +
                "'%s' with expired token.", username, urlPath);
    }

    public static String blacklistedToken(String username, String urlPath) {
        return String.format("User with username: '%s' tried to access url endpoint: " +
                "'%s' with blacklisted token.", username, urlPath);
    }

    public static String deviceMessErr() {
        return "An error occurred while reading messages from devices.";
    }

    public static String deviceReadTimeUpdate(String user, String deviceName, Integer to) {
        return String.format("User with username: '%s' updated read time from device: '%s' to '%s' seconds.", user, deviceName, to);
    }

    public static String saveCertMess(String username, CertificateDataDTO dto) {
        return String.format("""
                        Admin with username: '%s' signed certificate with data:
                        Email: '%s'
                        Common name: '%s'
                        Organization: '%s'
                        Organization unit: '%s'
                        Country: '%s'
                        State: '%s'""",
                username, dto.getEmail(), dto.getCommonName(), dto.getOrganization(),
                dto.getOrganizationUnit(), dto.getCountry(), dto.getState());
    }

    public static String revokeCertMess(String username, RevokedCertificateDTO dto) {
        return String.format("Admin with username: '%s' revoked certificate with alias: '%s'." +
                "\nReason: %s", username, dto.getAlias(), dto.getReason());
    }

    public static String saveCsrMess(CSR csr) {
        return String.format("""
                        Certificate signing request arrived with data:
                        Email: '%s'
                        Common name: '%s'
                        Organization: '%s'
                        Organization unit: '%s'
                        Country: '%s'
                        State: '%s'""",
                csr.getEmail(), csr.getCommonName(), csr.getOrganization(),
                csr.getOrganizationUnit(), csr.getCountry(), csr.getState());
    }

    public static String saveCsrErrMess(String message, CSR csr) {
        return String.format("""
                        Certificate signing request arrived and error occurred:
                        Message: '%s'
                        Data:
                        - Email: '%s'
                        - Common name: '%s'
                        - Organization: '%s'
                        - Organization unit: '%s'
                        - Country: '%s'
                        - State: '%s'""",
                message,
                csr.getEmail(), csr.getCommonName(), csr.getOrganization(),
                csr.getOrganizationUnit(), csr.getCountry(), csr.getState());
    }

    public static String saveCsrInternalErrMess(CSR csr) {
        return String.format("""
                        Certificate signing request arrived and internal server error occurred.
                        Data:
                        - Email: '%s'
                        - Common name: '%s'
                        - Organization: '%s'
                        - Organization unit: '%s'
                        - Country: '%s'
                        - State: '%s'""",
                csr.getEmail(), csr.getCommonName(), csr.getOrganization(),
                csr.getOrganizationUnit(), csr.getCountry(), csr.getState());
    }

    public static String csrVerification(Long id) {
        return String.format("Certificate signing request with id: '%s' successfully verified ", id);
    }

    public static String csrVerificationErr(String message, Long id) {
        return String.format("Error occurred while verifying certificate signing request with id: '%s' with" +
                " message: '%s'", message, id);
    }

    public static String certValidationSucc(String username, String alias) {
        return String.format("Admin with username: '%s' ran validation for certificate with alias: '%s', " +
                "and validation was successful.", username, alias);
    }

    public static String certValidationEx(String username, String alias, String message) {
        return String.format("Admin with username: '%s' ran validation for certificate with alias: '%s', " +
                "and validation was unsuccessful." +
                "\nMessage: '%s'", username, alias, message);
    }

    public static String unSuccCsrGet(String username, Long id) {
        return String.format("Admin with username: '%s' requested csr with invalid id: '%s'", username, id);
    }


}
