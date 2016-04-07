package com.aehtiopicus.cens;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.aehtiopicus.cens.configuration.MailConfigDev;
import com.aehtiopicus.cens.configuration.PersistenceJPAConfigDev;

@Configuration
@Import(value={PersistenceJPAConfigDev.class,MailConfigDev.class})
@ImportResource({"classpath:/servlet-context.xml"})
public class TestContextConfig {
	static{
		System.setProperty("spring.profiles.active", "development");
		System.out.println(System.getProperty("spring.profiles.active"));
	}
}
