package com.aehtiopicus.cens;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.repository.cens.PerfilCensRepository;
import com.aehtiopicus.cens.repository.cens.UsuariosCensRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class TestTest {

	@Autowired
	private UsuariosCensRepository ur;
	@Autowired
	private PerfilCensRepository pr;
	
	@Test
	public void test(){
		
		Usuarios u = new Usuarios();
		u = ur.save(u);
		Perfil p = new Perfil();
		p.setUsuario(u);
		pr.save(p);
		u = ur.findOne(u.getId());
		u.setPerfil(null);
		u = ur.save(u);
		
		List<Perfil> perfiles =pr.findByUsuario(u);
		if(perfiles.get(0).getId().equals(p.getId())){
			System.out.println("soy feliz");
		}
		
	}
}
