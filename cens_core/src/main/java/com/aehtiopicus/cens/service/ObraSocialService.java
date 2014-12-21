package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.ObraSocial;

@Service
public interface ObraSocialService {

		
	public List<ObraSocial> getObrasSociales();
	
	public ObraSocial saveObraSocial(ObraSocial obraSocial);

	public ObraSocial getObraSocialByNombre(String nombre);

	public List<ObraSocial> search(String nombre, Integer page, Integer rows);

	public int getTotal(String nombre);

	public ObraSocial getObraSocialById(Long obraSocialId);

	public boolean deleteObraSocial(Long obraSocialId);
}
