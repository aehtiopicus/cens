package com.aehtiopicus.cens.configuration;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aehtiopicus.profiles.Production;

@Configuration
@EnableTransactionManagement
@Production
public class JasyptConfigProd {




	@Bean(name="strongEncryptor")
	public PooledPBEStringEncryptor getStrongEncryptorBean() {
		
		PooledPBEStringEncryptor bean = new PooledPBEStringEncryptor();
		
		bean.setAlgorithm("PBEWithMD5AndDES");
		bean.setPassword("muitavon");
		bean.setPoolSize(4);
		
		return bean;
	}

	@Bean(name = "hibernateStringEncryptor")
	public HibernatePBEStringEncryptor getHibernateStringEncryptorBean() {
		HibernatePBEStringEncryptor bean = new HibernatePBEStringEncryptor();
	
		bean.setRegisteredName("hibernateStringEncryptor");
		bean.setEncryptor(getStrongEncryptorBean());
		
		return bean;
	}

}
