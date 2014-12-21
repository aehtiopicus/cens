package com.aehtiopicus.cens.validator;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.UsuarioDto;
import com.aehtiopicus.cens.service.UsuarioService;

@Service
public class UsuarioValidator implements Validator {

	@Autowired
	private UsuarioService usuarioService;

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	protected static ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UsuarioDto usuarioDto = (UsuarioDto) target;

		Usuario usuario = usuarioService.getUsuarioByUsername(usuarioDto.getUsername());
		if (usuario != null) {
			if(usuarioDto.getUsuarioId() == null || usuarioDto.getUsuarioId() < 1){
				errors.rejectValue( "username", "username.duplicado", mensajes.getString("ERR021"));
			}else if(!usuarioDto.getUsuarioId().equals(usuario.getId())){
				errors.rejectValue( "username", "username.duplicado", mensajes.getString("ERR021"));				
			}
		}

		if(usuarioDto.getUsuarioId() == null) {
			if(StringUtils.isEmpty(usuarioDto.getPassword())) {
				errors.rejectValue( "password","password.empty", mensajes.getString("ERR028"));							
			}
			if(StringUtils.isEmpty(usuarioDto.getPasswordConfirmation())) {
				errors.rejectValue( "passwordConfirmation","passwordConfirmation.empty", mensajes.getString("ERR029"));							
			}
			
			if(!usuarioDto.getPassword().equals(usuarioDto.getPasswordConfirmation())){
				errors.rejectValue( "passwordConfirmation","password.confirmation.failed", mensajes.getString("ERR022"));			
			}
			
		}
	}

}
