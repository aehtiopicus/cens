package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.repository.cens.UsuariosCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class UsuarioCensServiceImpl implements UsuarioCensService{
	
	@Autowired
	private UsuariosCensRepository usuariosCensRepository;
	
	@Override
	public Usuarios saveUsuario(Usuarios usuarios) throws CensException{
		try{
		if(usuarios==null){
			throw new CensException("No se puede guardar el usuario");
		}
		return usuariosCensRepository.save(usuarios);
		}catch(CensException e){
			throw e;
		}catch(Exception e){
			throw new CensException ("No se puede guardar el usuario ",e);
		}
	}
	

}
