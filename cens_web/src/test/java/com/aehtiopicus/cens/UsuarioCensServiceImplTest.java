package com.aehtiopicus.cens;



import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;
import com.aehtiopicus.cens.utils.CensException;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class UsuarioCensServiceImplTest {

	@Autowired
	private UsuarioCensService usuarioCensService;
	private Usuarios usuario;
	@Before
	public void setUp(){
		usuario = new Usuarios();
		MockitoAnnotations.initMocks(this);
		usuario.setEnabled(true);
		usuario.setPassword(Mockito.anyString());
		usuario.setUsername(Mockito.anyString());
	}
	
	@Test
	public void testCreation() throws Exception{		
		usuario =usuarioCensService.saveUsuario(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected = CensException.class)
	public void testCreationNull() throws Exception{		
		usuario =usuarioCensService.saveUsuario(null);
		Assert.assertNotNull(usuario.getId());
	}
	
	
}
