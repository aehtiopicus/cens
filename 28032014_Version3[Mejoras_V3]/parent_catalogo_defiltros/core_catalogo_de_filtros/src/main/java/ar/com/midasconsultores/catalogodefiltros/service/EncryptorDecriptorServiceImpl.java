/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Javier
 */
@Service
public class EncryptorDecriptorServiceImpl implements EncryptorDecriptorService {

    private static final String PBE_WITH_MD5_AND_DES = "PBEWithMD5AndDES";
    
    @Override
    public HibernatePBEStringEncryptor getEncryptorDescriptor(String encriptationPassword) {
        HibernatePBEStringEncryptor hibernateStringEncryptor = new HibernatePBEStringEncryptor();
        hibernateStringEncryptor.setPassword(encriptationPassword);
        hibernateStringEncryptor.setAlgorithm(PBE_WITH_MD5_AND_DES);
        return hibernateStringEncryptor;
    }
}
