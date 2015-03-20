package com.aehtiopicus.cens.service.cens;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.repository.cens.CambioEstadoCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class CambioEstadoCensServiceImpl implements CambioEstadoCensService{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private  CambioEstadoCensRepository cambioEstadoCensRepository;
	
	private static final String MAX_DAYS_NOT_SEEN = "#{notificacionProperties['max_no_notificado']}";
	
	@Value(MAX_DAYS_NOT_SEEN)
	private int days;
	
	
	@Override
	public CambioEstadoCensFeed save(CambioEstadoCensFeed aux)
			throws CensException {
		return cambioEstadoCensRepository.save(aux);		
	}

}
