package com.aehtiopicus.cens;

import java.util.Arrays;

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

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.AsesorCensService;
import com.aehtiopicus.cens.service.cens.MiembroCensService;
import com.aehtiopicus.cens.utils.CensException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class AsesorCensServiceImplTest {

	@Autowired
	private AsesorCensService asesorCensService;
	
	@Autowired
	private MiembroCensService miembroCensService;
	
	private MiembroCens miembroCens;
	private Usuarios usuario;
	
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
		miembroCens.setUsuario(usuario);
	}
	
	@Test
	public void testSave() throws Exception{
		miembroCens = miembroCensService.saveMiembroSens(Arrays.asList(miembroCens)).get(0);
		Perfil perfil = new Perfil();
		perfil.setPerfilType(PerfilTrabajadorCensType.ASESOR);
		miembroCens.getUsuario().setPerfil(Arrays.asList(perfil));
		Asesor a =asesorCensService.saveAsesor(miembroCens);
		Assert.assertNotNull(a);
		Assert.assertTrue(a.getMiembroCens().getId().equals(miembroCens.getId()));
	}
	
	@Test(expected = CensException.class)
	public void testSaveWithoutAsesor() throws Exception{
		miembroCens = miembroCensService.saveMiembroSens(Arrays.asList(miembroCens)).get(0);
		Perfil perfil = new Perfil();
		perfil.setPerfilType(PerfilTrabajadorCensType.PRECEPTOR);
		miembroCens.getUsuario().setPerfil(Arrays.asList(perfil));
		Asesor a =asesorCensService.saveAsesor(miembroCens);
		Assert.assertNotNull(a);
		Assert.assertTrue(a.getMiembroCens().getId().equals(miembroCens.getId()));
	}
	
}
