package com.aehtiopicus.cens;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.configuration.MailConfigDev;
import com.aehtiopicus.cens.configuration.PersistenceJPAConfigDev;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class UsuarioCensServiceImplTest {

	@Autowired
	private UsuarioCensService usuarioCensService;
	
	@Test
	public void testSomething(){
//		Assert.assertNotNull(usuarioCensService);
	}
	
	@Configuration
	@Import(value={PersistenceJPAConfigDev.class,MailConfigDev.class})
	@ImportResource({"file:src/main/webapp/WEB-INF/spring/config/servlet-context.xml"})
	public static class ContextConfig {

	}
}
