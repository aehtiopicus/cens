package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

public interface ComentarioCensService {

	MiembroCens getMiembroCensByPerfilTypeAndRealId(
			PerfilTrabajadorCensType type, Long id) throws CensException;

}
