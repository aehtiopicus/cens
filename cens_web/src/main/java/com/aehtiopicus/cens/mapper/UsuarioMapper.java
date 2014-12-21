package com.aehtiopicus.cens.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.domain.UsuarioPerfil;
import com.aehtiopicus.cens.dto.ChangePasswordDto;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.MiPerfilDto;
import com.aehtiopicus.cens.dto.ResetPasswordDto;
import com.aehtiopicus.cens.dto.UsuarioDto;

public class UsuarioMapper {

	public static List<ComboDto> getCombosDtoFromPerfiles(List<Perfil> perfiles){
		List<ComboDto> comboData = new ArrayList<ComboDto>();
		
		if(perfiles != null){
			ComboDto data;
			for (Perfil perfil : perfiles) {
				data = new ComboDto();
				data.setId(perfil.getId());
				data.setValue(perfil.getNombre());
				comboData.add(data);
			}
		}
		
		return comboData;
	}

	public static Usuario getUsuarioFromUsuarioDto(UsuarioDto dto) {
		Usuario usuario = new Usuario();
		Md5PasswordEncoder md5pe = new Md5PasswordEncoder();
		
		if(dto != null){
			if(!StringUtils.isEmpty(dto.getNombre())){
				usuario.setNombre(dto.getNombre());
			}
			if(!StringUtils.isEmpty(dto.getApellido())){
				usuario.setApellido(dto.getApellido());
			}
			if(!StringUtils.isEmpty(dto.getEmail())){
				usuario.setMail(dto.getEmail());
			}
			if(!StringUtils.isEmpty(dto.getPassword())){
				usuario.setPassword((md5pe.encodePassword(dto.getPassword(),null)));
			}
			if(!StringUtils.isEmpty(dto.getUsername())){
				usuario.setUsername(dto.getUsername());
			}
			if(dto.getUsuarioId() != null){
				usuario.setId(dto.getUsuarioId());
			}
		}

		usuario.setEnabled(true);

		return usuario;
	}

	public static Perfil getPerfilFromUsuarioDto(UsuarioDto dto) {
		Perfil perfil = new Perfil(); 
		
		if(dto != null && dto.getPerfilId() != null){
			perfil.setId(dto.getPerfilId());
		}
		
		return perfil;
	}

	public static List<UsuarioDto> getUsuariosDtoFromUsuariosPerfil(List<UsuarioPerfil> usuariosPerfil){
		List<UsuarioDto> usuariosDto = new ArrayList<UsuarioDto>();
		if(!CollectionUtils.isEmpty(usuariosPerfil)){
			for(UsuarioPerfil us : usuariosPerfil){
				usuariosDto.add(getUsuarioDtoFromUsuarioPerfil(us));
			}
		}
		return usuariosDto;
	}

	public static UsuarioDto getUsuarioDtoFromUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
		Usuario usuario = usuarioPerfil.getUsuario();
		Perfil perfil = usuarioPerfil.getPerfil();
		
		UsuarioDto dto = new UsuarioDto();
		
		if(usuario != null){
			if(usuario.getId() > 0){
				dto.setUsuarioId(usuario.getId());
			}
			if(!StringUtils.isEmpty(usuario.getApellido())){
				dto.setApellido(usuario.getApellido());
			}
			if(!StringUtils.isEmpty(usuario.getMail())){
				dto.setEmail(usuario.getMail());
			}
			if(!StringUtils.isEmpty(usuario.getNombre())){
				dto.setNombre(usuario.getNombre());
			}
			if(!StringUtils.isEmpty(usuario.getUsername())){
				dto.setUsername(usuario.getUsername());
			}
		}
		
		if(perfil != null && perfil.getId() > 0){
			dto.setPerfilId(perfil.getId());
			dto.setProfileName(perfil.getNombre());
		}
		
		return dto;
	}

	public static MiPerfilDto getMiPerfilDtoFromUsuario(Usuario usuario) {
		MiPerfilDto dto = new MiPerfilDto();
		
		if(usuario != null){
			dto.setApellido(usuario.getApellido());
			dto.setEmail(usuario.getMail());
			dto.setNombre(usuario.getNombre());
			dto.setUsername(usuario.getUsername());
		}
		
		return dto;
	}

	public static Usuario updateUsuarioFromMiPerfilDto(MiPerfilDto dto, Usuario usuario) {
		
		if(dto != null){
			usuario.setApellido(dto.getApellido());
			usuario.setNombre(dto.getNombre());
			usuario.setMail(dto.getEmail());
		}
		
		return usuario;
	}

	public static ChangePasswordDto getChangePasswordDtoFromUsuario(Usuario usuario) {
		ChangePasswordDto dto = new ChangePasswordDto();
		
		if(usuario != null){
			dto.setUsername(usuario.getUsername());
		}
		return dto;
	}
	
	public static Usuario updateUsuarioFromChangePasswordDto(ChangePasswordDto dto, Usuario usuario){
		Md5PasswordEncoder md5pe = new Md5PasswordEncoder();
		if(dto != null){
			usuario.setPassword((md5pe.encodePassword(dto.getPasswordNuevo(),null)));
		}
		return usuario;
	}

	public static Usuario updateUsuarioFromUsuarioDto(Usuario entity, UsuarioDto dto) {
		if(dto != null){
			entity.setApellido(dto.getApellido());
			entity.setNombre(dto.getNombre());
			entity.setMail(dto.getEmail());
			entity.setUsername(dto.getUsername());
		}
		
		return entity;
	}

	public static ResetPasswordDto getResetPasswordDtoFromUsuario(Usuario entity) {
		ResetPasswordDto dto = new ResetPasswordDto();
		if(entity != null) {
			dto.setUsername(entity.getUsername());
			dto.setUsuarioId(entity.getId());
		}
		return dto;
	}

	public static Usuario updateUsuarioFromResetPassword(Usuario entity, ResetPasswordDto dto) {
		Md5PasswordEncoder md5pe = new Md5PasswordEncoder();
		if(dto != null){
			entity.setPassword((md5pe.encodePassword(dto.getPasswordNuevo(),null)));
		}
		return entity;
	}
}
