package com.aehtiopicus.cens;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.aehtiopicus.cens.configuration.MailConfigDev;
import com.aehtiopicus.cens.configuration.PersistenceJPAConfigDev;

@Configuration
@Import(value={PersistenceJPAConfigDev.class,MailConfigDev.class})
@ImportResource({"file:src/main/webapp/WEB-INF/spring/config/servlet-context.xml"})
public class ContextConfig {

}
