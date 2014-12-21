package com.aehtiopicus.cens.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.MotivoBaja;
import com.aehtiopicus.cens.repository.MotivoBajaRepository;



@Service
@Transactional
public class MotivoBajaServiceImpl implements MotivoBajaService{

	 
	@Autowired
	protected MotivoBajaRepository motivoBajaRepository;
						
	public void setMotivoBajaRepository(MotivoBajaRepository motivoBajaRepository) {
		this.motivoBajaRepository = motivoBajaRepository;
	}

				
	@Override
	public List<MotivoBaja> getMotivosBaja() {
		return motivoBajaRepository.findAll(new Sort("motivo"));
	}

	@Override
	public MotivoBaja saveMotivoBaja(MotivoBaja motivoBaja) {
		return motivoBajaRepository.saveAndFlush(motivoBaja);
	}

	@Override
	public boolean deleteMotivoBaja(Long motivoBajaId) {
		try {
			motivoBajaRepository.delete(motivoBajaId);
			return true;
		}catch(Exception e) {
			throw e;
		}
	}


	@Override
	public MotivoBaja getMotivoBajaById(Long motivoBajaId) {
		return motivoBajaRepository.findOne(motivoBajaId);
	}

	
}
