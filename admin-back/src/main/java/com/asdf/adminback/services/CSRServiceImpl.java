package com.asdf.adminback.services;

import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;
import com.asdf.adminback.repositories.CSRRepository;
import com.asdf.adminback.util.CSRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
public class CSRServiceImpl implements CSRService {

    @Autowired
    private CSRRepository csrRepository;

    @Autowired
    private MailService mailService;

    public static final String LINK_ROOT = "http://localhost:8080/api/csrs/verify-csr/";

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

        try {
            csr = csrRepository.save(csr);
            mailService.sendmail("CSR verification", composeVerificationEmail(csr.getId()), csr.getEmail());
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
    public void verify(Long id) throws CSRException {
        CSR csr = csrRepository.findOneById(id);

        if(csr == null)
            throw new CSRException("Invalid csr id for verification.");

        csr.setVerified(true);
        csrRepository.save(csr);
    }

    private String composeVerificationEmail(Long id) {
        String message = """
                Certificate signing request was created using this email address. To verify your identity click on this link %s%s

                This is an automatically generated email – please do not reply to it. 
                ©secure-home""";

        return String.format(message, LINK_ROOT, id);
    }
}
