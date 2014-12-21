package com.aehtiopicus.cens.validator;


import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.dto.ResetPasswordDto;

@Service
public class ResetPasswordValidator implements Validator {

	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ResetPasswordDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ResetPasswordDto dto = (ResetPasswordDto) target;
		Md5PasswordEncoder md5pe = new Md5PasswordEncoder();


		if(!StringUtils.isEmpty(dto.getPasswordNuevoConfirmation()) && !dto.getPasswordNuevo().equals(dto.getPasswordNuevoConfirmation())){
			errors.rejectValue( "passwordNuevoConfirmation","password.confirmation.failed", mensajes.getString("ERR010") );			
		}
	}

}
