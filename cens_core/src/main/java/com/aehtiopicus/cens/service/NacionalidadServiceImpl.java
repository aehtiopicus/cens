package com.aehtiopicus.cens.service;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Nacionalidad;
import com.aehtiopicus.cens.repository.NacionalidadRepository;

@Service
@Transactional
public class NacionalidadServiceImpl implements NacionalidadService{

	 private static final Logger logger = Logger.getLogger(NacionalidadServiceImpl.class);
	 
	 @Autowired
	 protected NacionalidadRepository nacionalidadRepository;

	

	public void setNacionalidadRepository(
			NacionalidadRepository nacionalidadRepository) {
		this.nacionalidadRepository = nacionalidadRepository;
	}



	@Override
	public List<Nacionalidad> getNacionalidades() {
		return nacionalidadRepository.findAll(new Sort("nombre"));
	}



	@Override
	public void saveNacionalid(Nacionalidad nacionalidad) {
		nacionalidadRepository.save(nacionalidad);
		
	}



	@Override
	public Nacionalidad getNacionalidadByName(String nombre) {
		return nacionalidadRepository.findByNombreIgnoreCase(nombre);
		
	}

	
}
