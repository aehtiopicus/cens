package com.aehtiopicus.cens.controller.cens.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class ProfesorCensValidator {

	private static final Logger logger = LoggerFactory.getLogger(ProfesorCensValidator.class);
	public void validate(List<Profesor> profesorList)throws CensException{		
		logger.info("validando profesores");
		if(CollectionUtils.isEmpty(profesorList)){
			throw new CensException("No hay datos del profesor");
		}
	}
}
