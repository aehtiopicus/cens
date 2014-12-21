package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.domain.UsuarioPerfil;

@Service
public interface UsuarioService {

	public void saveUsuario(Usuario usuario, Perfil perfil);
	
	public void saveUsuario(Usuario usuario);

	
	public List<Perfil> getPerfiles();
	
	public UsuarioPerfil getUsuarioPerfilByUsuarioId(Long usuarioId);
	
	public List<UsuarioPerfil> search(String searchTerm, String apellido, int pageIndex, int row);

	public int getTotalUsersFilterByProfile(String id, String apellido);

		
	public void deleteUsuario(Usuario usuario);

	public Usuario getUsuarioByUsername(String username);

	List<Usuario> getUsuariosByPerfil(Perfil perfil);

	public Usuario getById(Long usuarioId);

	
}
