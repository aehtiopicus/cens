/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.com.midasconsultores.catalogodefiltros.service;

import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;

/**
 *
 * @author Javier
 */
public interface EncryptorDecriptorService {
    
     public HibernatePBEStringEncryptor getEncryptorDescriptor(String encriptationPassword);
}
