package com.aehtiopicus.cens.validator;

import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.dto.IncrementoSalarialDto;
import com.aehtiopicus.cens.util.Utils;

@Service
public class IncrementoSalarialValidator implements Validator {
	private static final Logger logger = Logger.getLogger(IncrementoSalarialValidator.class);
	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	
	@Override
	public boolean supports(Class<?> clazz) {
		return IncrementoSalarialDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("validando incremento salarial");
		IncrementoSalarialDto dto = (IncrementoSalarialDto) target;
		
		
		
		if(StringUtils.isNotEmpty(dto.getIncrementoBasico())){
			try{
				Double valor = Double.valueOf(dto.getIncrementoBasico());	
				if(dto.getTipoIncremento().equals("%") && (valor < 0d || valor > 100d)) {
					errors.rejectValue( "incrementoBasico", "dto.incrementoBasico", mensajes.getString("ERR070"));					
				}else if(dto.getTipoIncremento().equals("$") && valor < 0d) {
					errors.rejectValue( "incrementoBasico", "dto.incrementoBasico", mensajes.getString("ERR071"));										
				}
			}catch(Exception e){
				errors.rejectValue( "incrementoBasico", "dto.incrementoBasico", mensajes.getString("ERR019"));
			}
		}

		if(StringUtils.isNotEmpty(dto.getIncrementoPresentismo())){
			try{
				Double valor = Double.valueOf(dto.getIncrementoPresentismo());	
				if(dto.getTipoIncremento().equals("%") && ( valor < 0d || valor > 100d)) {
					errors.rejectValue( "incrementoPresentismo", "dto.incrementoPresentismo", mensajes.getString("ERR070"));					
				}else if(dto.getTipoIncremento().equals("$") && valor < 0d) {					
					errors.rejectValue( "incrementoPresentismo", "dto.incrementoPresentismo", mensajes.getString("ERR071"));										
				}
			}catch(Exception e){
				errors.rejectValue( "incrementoPresentismo", "dto.incrementoPresentismo", mensajes.getString("ERR019"));
			}
		}
		
		if(StringUtils.isNotEmpty(dto.getFechaInicio())){
			try{
				Utils.sdf.parse(dto.getFechaInicio());	
			}catch(Exception e){
				errors.rejectValue( "fechaInicio", "dto.fechaInicio", mensajes.getString("ERR005"));
			}
		}else {
			errors.rejectValue( "fechaInicio", "dto.fechaInicio", mensajes.getString("ERR025"));			
		}
	}

}
