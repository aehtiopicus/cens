package com.aehtiopicus.cens.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import com.aehtiopicus.cens.service.cens.EmailCensService;
import com.aehtiopicus.cens.service.cens.EmailCensServiceImpl;
import com.aehtiopicus.profiles.Development;

@Configuration
@EnableTransactionManagement
@Development
public class MailConfigDev {

	//private static final Logger log = Logger.getLogger(MailConfigDev.class);

	private static final String GET_PROPFILE = "#{";
	private static final String GET_PROP_START = "['";
	private static final String GET_PROP_END = "']}";	
	private static final String MAIL_PROPERTIES_NAME = "mailProperties";
	
	private static final String MAIL_HOST = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "host" + GET_PROP_END;
	private static final String MAIL_USERNAME = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "user" + GET_PROP_END;
	private static final String MAIL_PORT = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "port" + GET_PROP_END;
	private static final String MAIL_PASSWORD = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "pass" + GET_PROP_END;
	private static final String MAIL_SMTP_AUTH = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "smtp.auth" + GET_PROP_END;
	private static final String MAIL_SMTP_STARTTLS = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "smtp.starttls.enable" + GET_PROP_END;
	private static final String MAIL_FROM = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "mail.from" + GET_PROP_END;	

	private static final String VELICITY_TEMPLATES = GET_PROPFILE + MAIL_PROPERTIES_NAME + GET_PROP_START + "velocity.templates.path" + GET_PROP_END;

	
	
	@Value(MAIL_HOST) private String host;
	@Value(MAIL_USERNAME) private String username;
	@Value(MAIL_PORT) private String port;
	@Value(MAIL_PASSWORD) private String password;
	@Value(MAIL_SMTP_AUTH) private String smtpAuth;
	@Value(MAIL_SMTP_STARTTLS) private String smtpStarttls;
	@Value(MAIL_FROM) private String mailFrom;	
	@Value(VELICITY_TEMPLATES) private String velocityTemplatesPath;


	@Bean(name="mailSender")
	public JavaMailSenderImpl getMailSenderBean() {
		
		JavaMailSenderImpl bean = new JavaMailSenderImpl();
		
		bean.setHost(host);
		bean.setPort(Integer.valueOf(port));
		bean.setUsername(username);
		bean.setPassword(password);
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.starttls.enable", smtpStarttls);
		
		bean.setJavaMailProperties(props);
		
		return bean;
	}

	@Bean(name = "velocityEngine")
	public VelocityEngineFactoryBean getVelocityEngineBean() {
		VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
		
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		
		bean.setVelocityProperties(props);
		
		return bean;
	}
	
	@Bean(name="emailService")
	public EmailCensService getEmailServiceBean() throws VelocityException, IOException {
		EmailCensServiceImpl bean = new EmailCensServiceImpl();
		
		bean.setMailSender(getMailSenderBean());
		bean.setVelocityEngine(getVelocityEngineBean().getObject());
		bean.setFrom(mailFrom);		
		bean.setPathTemplates(velocityTemplatesPath);
		return bean;
	}

}
