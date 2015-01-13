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
			validate(usuarios);
			return usuariosCensRepository.save(usuarios);
		}catch(CensException e){
			throw e;
		}catch(Exception e){
			throw new CensException ("No se puede guardar el usuario ",e);
		}
	}

	private void validate(Usuarios usuarios ) throws CensException{
		if(usuarios==null){
			throw new CensException("No se puede guardar el usuario");
		}
		usuarios.setUsername(usuarios.getUsername().toLowerCase());
		Usuarios u = findUsuarioByUsername(usuarios.getUsername());
		
		if(u!=null && (usuarios.getId()==null || !u.getId().equals(usuarios.getId()))){
			throw new CensException("No se puede guardar el usuario","username","El nombre de usuario ya existe");
		}
	}
	@Override
	public void deleteUsuarioByMiembroId(Long miembroId) {
		usuariosCensRepository.softDeleteByMiembro(miembroId);
		
	}
	
	@Override
	public Usuarios findUsuarioByUsername(String username){
		return usuariosCensRepository.findByUsername(username);
	}

	@Override
	public Usuarios findUsuarioById(Long id) {
		return usuariosCensRepository.findOne(id);
	}
	

}
