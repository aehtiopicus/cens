package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.MotivoBaja;

@Service
public interface MotivoBajaService {

	public List<MotivoBaja> getMotivosBaja();
	
	public MotivoBaja saveMotivoBaja(MotivoBaja motivoBaja);

	public boolean deleteMotivoBaja(Long motivoBajaId);
	
	public MotivoBaja getMotivoBajaById(Long motivoBajaId);
}
