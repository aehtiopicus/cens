package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.utils.CensException;

public interface MiembroCensService {
	public MiembroCens saveMiembroSens(MiembroCens miembroCens)
			throws CensException;


	public MiembroCens searchMiembroCensByUsuario(Usuarios usuario);
}
