package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Beneficio;


@Service
public interface BeneficioService {

	public void saveBeneficio(Beneficio beneficio);
	
	public List<Beneficio> getBeneficios();

	public Beneficio getBeneficioByTitulo(String titulo);
}
