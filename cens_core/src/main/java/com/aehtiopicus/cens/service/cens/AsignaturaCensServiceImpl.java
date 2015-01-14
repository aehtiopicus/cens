package com.aehtiopicus.cens.service.cens;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.repository.cens.AsignaturCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class AsignaturaCensServiceImpl implements AsignaturaCensService{

	@Autowired
	private AsignaturCensRepository asignaturaCensRepository;
	@Autowired
	private CursoCensService cursoCensService;
	@Autowired
	private ProfesorCensService profesorCensService;
	
	public List<Asignatura> saveAsignaturas(List<Asignatura> asignaturaList) throws CensException{
		
	}
}
