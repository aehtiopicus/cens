package com.aehtiopicus.cens.controller.cens.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.dto.cens.PasswordChangeDto;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class UsuarioCensValidator {

	public void validate(Usuarios usuario) throws CensException{
		if(usuario == null){
			throw new CensException("No hay datos de usuario");
		}
		Map<String, String> errorMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(usuario.getUsername())) {
			errorMap.put("username", "Nombre de Usuario es requerido");
		}

		if (StringUtils.isEmpty(usuario.getPassword())) {
			errorMap.put("password",
					"Contrase&ntilde;a de Usuario es requerida");
		} else {
			if (usuario.getPassword().length() < 6) {
				errorMap.put("password",
						"Contrase&ntilde;a de Usuario debe tener almenos 6 caracteres");
			}
		}

		if (CollectionUtils.isEmpty(usuario.getPerfil())) {
			errorMap.put("perfil", "Se requiere perfil de usuario");
		}
		if(!errorMap.isEmpty()){
			throw new CensException("Error al crear el miembro del cens",errorMap);
		}
	}
	
	public void validateChangePass(Usuarios u, PasswordChangeDto passChangeDto) throws CensException{
		if(u== null){
			throw new CensException("El usuario es nulo");
		}
		if(passChangeDto == null){
			throw new CensException("No existe informaci&oacute;n de cambio de contrase&ntilde;a");
		}
		Map<String,String> errorMap = new HashMap<String,String>();
		if(StringUtils.isEmpty(passChangeDto.getOldPass())){
			errorMap.put("passwordOld", "Contrase&ntilde;a actual inv&aacute;lida");
		}else{
			if(!u.getPassword().equals(passChangeDto.getOldPass())){
				errorMap.put("passwordOld", "Contrase&ntilde;a actual inv&aacute;lida");
			}else{
				if(StringUtils.isEmpty(passChangeDto.getNewPass())){
					errorMap.put("password", "Contrase&ntilde;a nueva inv&aacute;lida");
				}
				if(StringUtils.isEmpty(passChangeDto.getOldPass())){
					errorMap.put("passwordNewConfirm", "Contrase&ntilde;a nueva inv&aacute;lida");
				}
				if(StringUtils.isNotEmpty(passChangeDto.getNewPass()) && StringUtils.isNotEmpty(passChangeDto.getNewPassConfirm())){
					if(!passChangeDto.getNewPass().equals(passChangeDto.getNewPassConfirm())){
						errorMap.put("password", "Contrase&ntilde;a no coincide");
					}else{
						u.setPassword(passChangeDto.getNewPass());
						try{
							validate(u);
						}catch(CensException e){
							errorMap.putAll(e.getError());
						}
					}
				}
			}
		}
		if(!errorMap.isEmpty()){
			throw new CensException("Error al crear el miembro del cens",errorMap);
		}
	}
}
