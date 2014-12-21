package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Prepaga;

@Service
public interface PrepagaService {

	public List<Prepaga> getPrepagas();

	public Prepaga save(Prepaga prepaga);
	
	public Prepaga getPrepagaByName(String nombre);
	
	public List<Prepaga> search(String nombre, Integer page, Integer rows);

	public int getTotal(String nombre);

	public Prepaga getPrepagaById(Long prepagaId);

	public boolean deletePrepaga(Long prepagaId);
	
}
