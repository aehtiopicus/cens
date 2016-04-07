package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

public interface PerfilCensService {

	public Perfil addPerfilToUser(Usuarios usuario, PerfilTrabajadorCensType perfilType);
	
	public List<Perfil> listPerfilFromUsuario(Usuarios usuario);

	public List<Perfil> addPerfilesToUsuarios(Usuarios usuario) throws CensException;

	public void removePerfiles(List<PerfilTrabajadorCensType> perfilTypeList,
			Usuarios usuario) throws CensException;

}
