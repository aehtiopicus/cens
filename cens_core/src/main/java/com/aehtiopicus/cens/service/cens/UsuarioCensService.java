package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.utils.CensException;

public interface UsuarioCensService {

	public Usuarios saveUsuario(Usuarios usuarios) throws CensException;
}
