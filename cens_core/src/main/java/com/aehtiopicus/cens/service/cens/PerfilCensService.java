package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

public interface PerfilCensService {

	Perfil addPerfilToUser(Usuarios usuario, PerfilTrabajadorCensType perfilType)
			throws CensException;

}
