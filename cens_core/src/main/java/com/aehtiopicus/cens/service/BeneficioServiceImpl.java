package com.aehtiopicus.cens.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.repository.BeneficioRepository;

@Service
@Transactional
public class BeneficioServiceImpl implements BeneficioService{
	
	private static final Logger logger = Logger.getLogger(InitLoadServiceImpl.class);

	@Autowired
	BeneficioRepository beneficioRepository;
	
	public void setBeneficioRepository(BeneficioRepository beneficioRepository) {
		this.beneficioRepository = beneficioRepository;
	}

	@Override
	public void saveBeneficio(Beneficio beneficio) {
		beneficioRepository.saveAndFlush(beneficio);
	}

	@Override
	public List<Beneficio> getBeneficios() {
		return beneficioRepository.findAll();
	}

	@Override
	public Beneficio getBeneficioByTitulo(String titulo) {
		return beneficioRepository.findByTituloIgnoreCase(titulo);
	}
	
	

}
