package com.aehtiopicus.cens;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.MiembroCensService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class MiembroCensServiceImplTest {

	@Autowired
	private MiembroCensService miembroCensService;
	
	private MiembroCens miembroCens;
	private Usuarios usuario;
	private List<PerfilTrabajadorCensType> ptctList;
	
	@Before
	public void setUp(){
		miembroCens = new MiembroCens();
		miembroCens.setApellido(Mockito.anyString());
		miembroCens.setDni(Mockito.anyString());
		miembroCens.setFechaNac(Mockito.any(java.util.Date.class));
		miembroCens.setNombre(Mockito.anyString());
		
		usuario = new Usuarios();
		usuario.setPassword(Mockito.anyString());
		usuario.setUsername(Mockito.anyString());
		
		ptctList =Arrays.asList(new PerfilTrabajadorCensType[]{PerfilTrabajadorCensType.ADMINISTRADOR,PerfilTrabajadorCensType.ASESOR});
	}
	@Test
	public void testSave() throws Exception{
		MiembroCens mc = miembroCensService.saveMiembroSens(miembroCens, usuario, ptctList);
		Assert.assertNotNull(mc);
		Assert.assertNotNull(mc.getUsusario());
		Assert.assertTrue(mc.getUsusario().getId()!=null);
		Assert.assertNotNull(mc.getUsusario().getPerfil());
		Assert.assertTrue(!mc.getUsusario().getPerfil().isEmpty());
	}
}
