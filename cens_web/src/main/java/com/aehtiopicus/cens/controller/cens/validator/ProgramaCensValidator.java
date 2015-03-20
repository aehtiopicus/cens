package com.aehtiopicus.cens.controller.cens.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.dto.cens.ProgramaDto;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class ProgramaCensValidator {

	@Autowired
	private FileUploadCensValidator fileUploadCensValidator;

	public void validate(ProgramaDto dto,MultipartFile mf) throws CensException {
		if (dto == null) {
			throw new CensException("No hay datos del programa");
		}
		if(mf!=null ){
			fileUploadCensValidator.validate(mf);
		}
	
		Map<String, String> errorMap = new HashMap<String, String>();

		if (StringUtils.isEmpty(dto.getNombre())) {
			errorMap.put("nombre", "Nombre es requerido");
		}
		if (dto.getCantCartillas()== null ||dto.getCantCartillas() <=0 || dto.getCantCartillas() >12 ) {
			errorMap.put("cantCartillas", "La cantidad de cartillas debe estar entre 1-12");
		}		

		if (!errorMap.isEmpty()) {
			throw new CensException("Error al guardar Programa de la Asignatura", errorMap);
		}
	}

	public void validateCambioEstado(EstadoRevisionType type)throws CensException {
		if(type==null ){
			throw new CensException("El estado del programa debe existir");
		}
		
		switch(type){
		case INEXISTENTE:
			throw new CensException("El estado del programa no puede ser "+ EstadoRevisionType.INEXISTENTE);
		case LISTO:
			throw new CensException("El estado del programa no puede ser "+ EstadoRevisionType.LISTO);
			
		case NUEVO:
			throw new CensException("El estado del programa no puede ser "+ EstadoRevisionType.NUEVO);
		default:
			break;
		
		}
	}

}
