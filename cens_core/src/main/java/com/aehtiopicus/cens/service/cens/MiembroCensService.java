package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.utils.CensException;

public interface MiembroCensService {
	public List<MiembroCens> saveMiembroSens(List<MiembroCens> miembroCens)
			throws CensException;


	public MiembroCens searchMiembroCensByUsuario(Usuarios usuario);


	public List<MiembroCens> listMiembrosCens(RestRequest restRequest);


	public MiembroCens getMiembroCens(Long id);


	public Long getTotalUsersFilterByProfile(RestRequest rr);


	public void deleteMiembro(Long miembroId);
}
