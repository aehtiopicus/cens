package com.aehtiopicus.cens.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aehtiopicus.profiles.Production;

@Configuration
@EnableTransactionManagement
//@ImportResource("classpath*:*spring-data-config.xml")
@EnableJpaRepositories(basePackages="com.aehtiopicus.cens.repository")
@Production
public class PersistenceJPAConfig {

	private static final Logger logger = Logger.getLogger(PersistenceJPAConfig.class);
	
	private static final String GET_PROPFILE = "#{";
	private static final String GET_PROP_START = "['";
	private static final String GET_PROP_END = "']}";	
	private static final String MAIL_PROPERTIES_NAME = "persistenceProperties";
	
	private static final String BASE_URL = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "base.url" + GET_PROP_END;
	private static final String BASE_USER = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "base.usuario" + GET_PROP_END;
	private static final String BASE_PASS = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "base.clave" + GET_PROP_END;
	
	@Value(BASE_URL) private String baseUrl;
	@Value(BASE_USER) private String baseUsuario;
	@Value(BASE_PASS) private String baseClave;


	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(restDataSource());
		factoryBean
				.setPackagesToScan(new String[] { "com.aehtiopicus.cens.domain" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {

			{
				// JPA properties
				setShowSql(true);
				setDatabase(Database.POSTGRESQL);
				setGenerateDdl(true);
			}
		};
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setJpaProperties(additionalProperties());

		return factoryBean;
	}

	@Bean(name = "dataSource")
	public DataSource restDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(baseUrl);
		dataSource.setUsername(baseUsuario);
		dataSource.setPassword(baseClave);
		
		logger.info("Info - Conexion de base de datos");
		logger.info("url: " + baseUrl);
		logger.info("usuario: " + baseUsuario);
		logger.info("clave: " + baseClave);
		
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean()
				.getObject());

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean 
	public HibernateExceptionTranslator hibernateExceptionTranslator(){ 
		return new HibernateExceptionTranslator(); 
	}
	 

	final Properties additionalProperties() {
		return new Properties() {

			/**
             *
             */
			private static final long serialVersionUID = 3996171551371239499L;

			{
				// use this to inject additional properties in the EntityManager
				setProperty("hibernate.hbm2ddl.auto", "update");
				setProperty("hibernate.show_sql","true");
			}
		};
	}
}
