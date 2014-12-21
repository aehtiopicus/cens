package com.aehtiopicus.cens.validator;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.dto.BeneficioClienteDto;

@Service
public class BeneficioClienteValidator implements Validator {


	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	

	@Override
	public boolean supports(Class<?> clazz) {
		return BeneficioClienteDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		BeneficioClienteDto dto = (BeneficioClienteDto) target;

		if(StringUtils.isNotEmpty(dto.getValor())) {
			try {
				Float.valueOf(dto.getValor());
			}catch(Exception e) {
				errors.rejectValue("valor","valor.errorType",mensajes.getString("ERR019"));
			}
		}
		
	

		
 }
}
