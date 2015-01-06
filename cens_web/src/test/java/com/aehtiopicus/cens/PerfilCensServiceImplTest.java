package com.aehtiopicus.cens;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.PerfilCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;
import com.aehtiopicus.cens.utils.CensException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class PerfilCensServiceImplTest {

	@Autowired
	@Qualifier("perfilCensServiceImpl")
	private PerfilCensService service;
	@Autowired
	private UsuarioCensService usuarioCensService; 
	private Usuarios usuario;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		usuario = new Usuarios();
		usuario.setId(Mockito.anyLong());
	}
	
	@Test
	public void testCreation() throws Exception{
		
		Perfil perfil =service.addPerfilToUser(usuarioCensService.saveUsuario(usuario), PerfilTrabajadorCensType.ADMINISTRADOR);
		Assert.assertNotNull(perfil);
	}
	
	@Test(expected= CensException.class)
	public void testCreationDuplicated() throws Exception{
		
		usuario = usuarioCensService.saveUsuario(usuario);
		for(int i = 0 ; i<2;i++){
			service.addPerfilToUser(usuario, PerfilTrabajadorCensType.ADMINISTRADOR);
		}
		
	}
	
	@Test
	public void testListPerfiles() throws Exception{
		usuario = usuarioCensService.saveUsuario(usuario);
		for(PerfilTrabajadorCensType perfilTrabajadorCensType : PerfilTrabajadorCensType.values()){
			service.addPerfilToUser(usuario, perfilTrabajadorCensType);
		}
		List<Perfil> perfiles =service.listPerfilFromUsuario(usuario);
		Assert.assertNotNull(perfiles);
		Assert.assertEquals(PerfilTrabajadorCensType.values().length, perfiles.size());
	}
}
