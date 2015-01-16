package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

public interface RolCensService {

	public void assignRolToMiembro(MiembroCens miembroCens) throws CensException;

	public void removeRolToMiembro(PerfilTrabajadorCensType perfil, Usuarios usuario) throws CensException;
	
	public List<PerfilTrabajadorCensType> listPerfil();
	
	public PerfilTrabajadorCensType getPerfilByName(String perfilName);

}
