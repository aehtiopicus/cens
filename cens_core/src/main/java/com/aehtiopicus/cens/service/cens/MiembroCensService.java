package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.utils.CensException;

public interface MiembroCensService {
	public List<MiembroCens> saveMiembroSens(List<MiembroCens> miembroCens)
			throws CensException;


	public MiembroCens searchMiembroCensByUsuario(Usuarios usuario);


	public List<MiembroCens> listMiembrosCens();


	public MiembroCens getMiembroCens(Long id);
}
