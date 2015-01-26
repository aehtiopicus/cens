package com.aehtiopicus.cens.controller.cens.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.dto.cens.ProgramaDto;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class ProgramaCensValidator {

	@Autowired
	private FileUploadCensValidator fileUploadCensValidator;

	public void validate(ProgramaDto dto,MultipartFile mf) throws CensException {
		if (dto == null) {
			throw new CensException("No hay datos del profesor");
		}
		if(mf!=null ){
			fileUploadCensValidator.validate(mf);
		}
	
		Map<String, String> errorMap = new HashMap<String, String>();

		if (StringUtils.isEmpty(dto.getNombre())) {
			errorMap.put("nombre", "Nombre es requerido");
		}
		if (dto.getCantCartillas()== null ||dto.getCantCartillas() <=0 || dto.getCantCartillas() >99 ) {
			errorMap.put("cantCartillas", "La cantidad de cartillas debe estar entre 1-99");
		}		

		if (!errorMap.isEmpty()) {
			throw new CensException("Error al guardar Programa de la Asignatura", errorMap);
		}
	}

}
