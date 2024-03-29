package com.asdf.adminback.services.impl;

import com.asdf.adminback.dto.CertificateDTO;
import com.asdf.adminback.models.IssuerData;
import com.asdf.adminback.services.KeyStoreService;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.asdf.adminback.util.Constants.*;

@Service
public class KeyStoreServiceImpl implements KeyStoreService {

    private KeyStore keyStore;

    @PostConstruct
    private void postConstruct(){
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @Override
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadKeyStore(String fileName, char[] password) {
        try {
            if (fileName != null) {
                keyStore.load(new FileInputStream(fileName), password);
            } else {
                keyStore.load(null, password);
            }
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveKeyStore(String fileName, char[] password) {
        try {
            keyStore.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);

            Certificate cert = keyStore.getCertificate(alias);

            PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
            return new IssuerData(privKey, issuerName);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException |
                UnrecoverableKeyException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());


            if (ks.isKeyEntry(alias)) {
                return ks.getCertificate(alias);
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException |
                CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if (ks.isKeyEntry(alias)) {
                return (PrivateKey) ks.getKey(alias, pass.toCharArray());
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException |
                CertificateException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAliases() throws KeyStoreException, NoSuchProviderException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(FILE_PATH));
        ks.load(in, PWD.toCharArray());

        List<String> temp = new ArrayList<>();
        Enumeration<String> en = ks.aliases();
        while(en.hasMoreElements())
            temp.add(en.nextElement());
        return temp;
    }

    @Override
    public List<CertificateDTO> readCertificateChain(String keyStoreFile, String keyStorePass, String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            List<Certificate> listOfCertificates = new ArrayList<>();

            if (ks.isKeyEntry(alias)) {
                if (alias.equals("root")) {
                    listOfCertificates.add(ks.getCertificate(alias));
                } else if (alias.equals("intermediate")) {
                    listOfCertificates.add(ks.getCertificate("root"));
                    listOfCertificates.add(ks.getCertificate(alias));
                } else {
                    listOfCertificates.add(ks.getCertificate("root"));
                    listOfCertificates.add(ks.getCertificate("intermediate"));
                    listOfCertificates.add(ks.getCertificate(alias));
                }

                List<CertificateDTO> certs = new ArrayList<>();
                for (Certificate c : listOfCertificates)
                    certs.add(new CertificateDTO((X509Certificate) c));
                return certs;
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException |
                CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long generateNextSerialNumber() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(FILE_PATH));
            ks.load(in, PWD.toCharArray());

            List<String> temp = new ArrayList<>();
            Enumeration<String> en = ks.aliases();

            while(en.hasMoreElements())
                temp.add(en.nextElement());

            return (long) (temp.size() + 1);
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean containsAlias(String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(FILE_PATH));
            ks.load(in, PWD.toCharArray());

            return ks.containsAlias(alias);
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        return false;
    }
}
