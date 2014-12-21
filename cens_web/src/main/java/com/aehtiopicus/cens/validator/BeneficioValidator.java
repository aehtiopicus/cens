package com.aehtiopicus.cens.validator;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.dto.BeneficioDto;
import com.aehtiopicus.cens.service.BeneficioService;

@Service
public class BeneficioValidator implements Validator{
	
	@Autowired
	private BeneficioService beneficioService;
	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");


	public void setBeneficioService(BeneficioService beneficioService) {
		this.beneficioService = beneficioService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return BeneficioDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BeneficioDto dto = (BeneficioDto) target;
		
		
		Beneficio beneficio = beneficioService.getBeneficioByTitulo(dto.getTitulo());
		
		if (beneficio != null) {
			if(dto.getBeneficioId() == null || dto.getBeneficioId() < 1){
				errors.rejectValue( "titulo", "titulo.duplicado", mensajes.getString("ERR033"));
			}else if(!dto.getBeneficioId().equals(beneficio.getId())){
				errors.rejectValue( "titulo", "titulo.duplicado", mensajes.getString("ERR033"));				
			}
		}
		
	}

	

}
