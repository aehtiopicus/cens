package com.aehtiopicus.cens.controller.cens.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class InscripcionAlumnoCensValidator {

	public void validateInscripcion(AsignaturaInscripcion ai) throws CensException {
		if(ai == null){
			throw new CensException("No hay datos de inscripci&oacute;n disponible");
		}
		if(ai.getAsignatura().getId() == null){
			throw new CensException("No hay datos de asignatura para inscripci&oacute;n disponible");
		}
		if(CollectionUtils.isEmpty(ai.getAlumnos())){
			throw new CensException("No hay datos de alumnos para inscripci&oacute;n disponible");
		}
		
	}
}
