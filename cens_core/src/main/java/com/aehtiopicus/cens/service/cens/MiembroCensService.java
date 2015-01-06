package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

public interface MiembroCensService {
	public MiembroCens saveMiembroSens(MiembroCens miembroCens,
			Usuarios usuario, List<PerfilTrabajadorCensType> perfilTypeList)
			throws CensException;
}
