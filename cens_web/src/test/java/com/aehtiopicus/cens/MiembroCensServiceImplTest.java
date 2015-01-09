package com.aehtiopicus.cens;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.aehtiopicus.cens.domain.entities.Perfil;
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
	private List<Perfil> perfilList;
	
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

		perfilList =Arrays.asList(createPerfil(PerfilTrabajadorCensType.ADMINISTRADOR),createPerfil(PerfilTrabajadorCensType.ASESOR));
		usuario.setPerfil(perfilList);
		miembroCens.setUsuario(usuario);
	}
	
	private Perfil createPerfil(PerfilTrabajadorCensType ptct){
		Perfil p = new Perfil();
		p.setPerfilType(ptct);
		return p;
	}
	@Test
	public void testSave() throws Exception{

		List<MiembroCens> mc = miembroCensService.saveMiembroSens(Arrays.asList(miembroCens));
		Assert.assertNotNull(mc);
		Assert.assertNotNull(mc.get(0).getUsuario());
		Assert.assertTrue(mc.get(0).getUsuario().getId()!=null);
		Assert.assertNotNull(mc.get(0).getUsuario().getPerfil());
		Assert.assertTrue(!mc.get(0).getUsuario().getPerfil().isEmpty());
	}
	
	@Test
	public void testSaveMiembroWithoutPerfil() throws Exception{
		miembroCens.getUsuario().setPerfil(null);
		List<MiembroCens> mc = miembroCensService.saveMiembroSens(Arrays.asList(miembroCens));
		Assert.assertNotNull(mc);
		Assert.assertNotNull(mc.get(0).getUsuario());
		Assert.assertTrue(mc.get(0).getUsuario().getId()!=null);
		Assert.assertTrue(CollectionUtils.isEmpty(mc.get(0).getUsuario().getPerfil()));
	}
}
