package com.asdf.adminback.services.impl;

import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;
import com.asdf.adminback.repositories.CSRRepository;
import com.asdf.adminback.services.CSRService;
import com.asdf.adminback.services.MailService;
import com.asdf.adminback.util.CSRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

@Service
public class CSRServiceImpl implements CSRService {

    static final String ATOZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    static final String D0TO9 = "0123456789";

    static final int SECURE_CODE_LEN = 15;

    static SecureRandom rnd = new SecureRandom();

    @Autowired
    private CSRRepository csrRepository;

    @Autowired
    private MailService mailService;

    @Override
    public List<CSR> findAll() {
        return csrRepository.findAll();
    }

    @Override
    public List<CSR> findAllVerified() {
        return csrRepository.findAllVerified();
    }

    @Override
    public CSR findOneById(Long id) {
        return csrRepository.findOneById(id);
    }

    @Override
    public CSR save(CSR csr) throws CSRException {
        CSRUtils.checkBasicCSRInfo(csr);
        csr.setVerified(false);

        String secureCode = randomString();

        while(csrRepository.findBySecurityCode(secureCode).isPresent()){
            secureCode = randomString();
        }

        csr.setSecurityCode(secureCode);

        try {
            csr = csrRepository.save(csr);
            mailService.sendmail("CSR verification", composeVerificationEmail(csr.getSecurityCode()), csr.getEmail());
        }
        catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new CSRException("Email or common name already exist in database.");
        }
        catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new CSRException("Error happened while sending verification email.");
        }

        return csr;
    }

    @Override
    public CSR verify(String securityCode) throws CSRException {
        CSR csr = csrRepository.findBySecurityCodeAndVerified(securityCode, false).orElse(null);

        if(csr == null)
            throw new CSRException("Invalid csr security code for verification.");

        csr.setVerified(true);
        return csrRepository.save(csr);
    }

    @Override
    public CSR findByEmail(String email) {
        return csrRepository.findByEmail(email);
    }

    @Override
    public void delete(CSR csr) {
        csrRepository.delete(csr);
    }

    private String composeVerificationEmail(String secureCode) {
        String message = """
                Certificate signing request was created using this email address. To verify your identity please insert 
                this security code into verification form and send verification request.
                
                Security code: %s

                This is an automatically generated email – please do not reply to it. 
                ©secure-home""";

        return String.format(message, secureCode);
    }

    private String randomString(){
        StringBuilder sb = new StringBuilder(SECURE_CODE_LEN);
        String AB = D0TO9 + ATOZ;
        for(int i = 0; i < SECURE_CODE_LEN; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
