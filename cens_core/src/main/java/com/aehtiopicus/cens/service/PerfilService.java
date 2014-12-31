package com.aehtiopicus.cens.service;

import java.util.List;

import com.aehtiopicus.cens.domain.Perfil;

public interface PerfilService {
	public Perfil getPerfileByPerfil(String name);

	public List<Perfil> listPerfil();
}
