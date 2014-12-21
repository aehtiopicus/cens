package com.aehtiopicus.cens.validator;


import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.ChangePasswordDto;
import com.aehtiopicus.cens.service.UsuarioService;

@Service
public class ChangePasswordValidator implements Validator {

	@Autowired
	private UsuarioService usuarioService;

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordDto dto = (ChangePasswordDto) target;
		Md5PasswordEncoder md5pe = new Md5PasswordEncoder();

		Usuario usuario = usuarioService.getUsuarioByUsername(dto.getUsername());	

		if(usuario != null && !StringUtils.isEmpty(dto.getPasswordNuevo()) && 
				!md5pe.encodePassword(dto.getPasswordActual(),null).equals(usuario.getPassword())){
			errors.rejectValue( "passwordActual", "password.incorrecto", mensajes.getString("ERR009"));
		}

		if(!StringUtils.isEmpty(dto.getPasswordNuevoConfirmation()) && !dto.getPasswordNuevo().equals(dto.getPasswordNuevoConfirmation())){
			errors.rejectValue( "passwordNuevoConfirmation","password.confirmation.failed", mensajes.getString("ERR010") );			
		}
	}

}
