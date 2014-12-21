package com.aehtiopicus.cens.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.repository.PerfilRepository;


@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {
	private static final Logger logger = Logger.getLogger(InitLoadServiceImpl.class);
	
	@Autowired
	protected PerfilRepository perfilRepository;
	
	@Override
	public Perfil getPerfileByPerfil(String name){
		return perfilRepository.findByPerfil(name);				
	}
}
