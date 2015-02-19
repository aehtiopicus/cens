package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.repository.cens.UsuariosCensRepository;

@Service
public class LoginCensServiceImpl implements LoginCensService{

	@Autowired
	private UsuariosCensRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Usuarios u = repository.findByUsername(username);
		if( u == null ){
			throw new UsernameNotFoundException( "No existe el usuario" );
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for(Perfil p :u.getPerfil()){
			authorities.add( new SimpleGrantedAuthority(p.getPerfilType().getNombre()));
		}
				

		return new User( u.getUsername(), u.getPassword(), authorities );
	}

}
