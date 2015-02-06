package com.aehtiopicus.cens.controller.cens.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.dto.cens.MaterialDidacticoDto;
import com.aehtiopicus.cens.enumeration.cens.DivisionPeriodoType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class MaterialDidacticoCensValidator {

	@Autowired
	private FileUploadCensValidator fileUploadCensValidator;

	public void validate(MaterialDidacticoDto dto,MultipartFile mf) throws CensException {
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
		if(StringUtils.isEmpty(dto.getDivisionPeriodoName())&& dto.getDivisionPeriodoType()==null){
			errorMap.put("divisionPeriodoType", "Debe indicarse un per&iacute;odo para el material");
		}
		if(StringUtils.isNotEmpty(dto.getDivisionPeriodoName())){
			DivisionPeriodoType dpt =DivisionPeriodoType.getDivisionByPeriodoName(dto.getDivisionPeriodoName());
			if(dpt==null){
				errorMap.put("divisionPeriodoType", "El tipo de per&iacute;odo no es correcto");	
			}else{
				dto.setDivisionPeriodoType(dpt);
			}
		}

		if (!errorMap.isEmpty()) {
			throw new CensException("Error al guardar el material did&aacute;ctico de la Asignatura", errorMap);
		}
	}

	public void validateCambioEstado(EstadoRevisionType type)throws CensException {
		if(type==null ){
			throw new CensException("El estado de la cartilla debe existir");
		}
		
		switch(type){
		case INEXISTENTE:
			throw new CensException("El estado del material no puede ser "+ EstadoRevisionType.INEXISTENTE);
		case LISTO:
			throw new CensException("El estado del material no puede ser "+ EstadoRevisionType.LISTO);
			
		case NUEVO:
			throw new CensException("El estado del material no puede ser "+ EstadoRevisionType.NUEVO);
		default:
			break;
		
		}
	}
}
