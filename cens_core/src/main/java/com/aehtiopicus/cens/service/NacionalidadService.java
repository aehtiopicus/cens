package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Nacionalidad;

@Service
public interface NacionalidadService {

	public List<Nacionalidad> getNacionalidades();
	
	public void saveNacionalid(Nacionalidad nacionalidad);

	public Nacionalidad getNacionalidadByName(String nombre);
	
	
}
