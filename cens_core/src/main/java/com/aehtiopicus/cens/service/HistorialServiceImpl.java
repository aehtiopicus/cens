package com.aehtiopicus.cens.service;


import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Vacaciones;
import com.aehtiopicus.cens.repository.EmpleadoRepository;
import com.aehtiopicus.cens.repository.SueldoRepository;
import com.aehtiopicus.cens.repository.VacacionesRepository;
import com.aehtiopicus.cens.specification.SueldoSpecification;

@Service
@Transactional
public class HistorialServiceImpl{

	 private static final Logger logger = Logger.getLogger(HistorialServiceImpl.class);
	 
	 public final static String DD_MM_YYYY = "dd/MM/yyyy";
	 
	 @Autowired
	 public RelacionLaboralService relacionLaboral;
		
	 public static SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
	 
	 @Autowired
	 protected EmpleadoRepository empleadoRepository;
	 
	 @Autowired
	 protected SueldoRepository sueldoRepository;
	 
	 @Autowired
	 protected VacacionesRepository vacacionesRepository;
	 
	 

	public void setVacacionesRepository(VacacionesRepository vacacionesRepository) {
		this.vacacionesRepository = vacacionesRepository;
	}

	public void setSueldoRepository(SueldoRepository sueldoRepository) {
		this.sueldoRepository = sueldoRepository;
	}

	public EmpleadoRepository getEmpleadoRepository() {
		return empleadoRepository;
	}
	public void setRelacionLaboral(RelacionLaboralService relacionLaboral) {
		this.relacionLaboral = relacionLaboral;
	}
	public void setEmpleadoRepository(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	

	public Sueldo getSueldoActualByEmpleadoId(Long empleadoId) {
		logger.info("getSueldoActualByEmpleadoId ");
		Specifications<Sueldo> spec = Specifications.where(SueldoSpecification.emptyFinalDateEquals());
		spec = spec.and(SueldoSpecification.empleadoEquals(empleadoId));
		List<Sueldo> sueldos = sueldoRepository.findAll(spec);
		Sueldo sueldo = null;
		if(!CollectionUtils.isEmpty(sueldos)){
			sueldo = sueldos.get(0);
		}
		return sueldo;
	}


	
	public Vacaciones getVacacionById(Long vacacionId) {
		return vacacionesRepository.findById(vacacionId);
		
	}

	



}
