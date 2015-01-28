package com.aehtiopicus.cens.mapper.cens;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.ComentarioDto;
import com.aehtiopicus.cens.dto.cens.ComentarioUserDto;
import com.aehtiopicus.cens.dto.cens.ComentariosDto;

@Component
public class ComentarioCensMapper {

	public ComentariosDto createCommentariosDto(Long usuarioId, MiembroCens mc) {
		ComentariosDto dto = new ComentariosDto();		
		dto.setResults(createComentarioDto(usuarioId,mc));
		return dto;
	}

	private ComentarioDto createComentarioDto(Long usuarioId, MiembroCens mc) {
		ComentarioDto dto = new ComentarioDto();
		dto.setUser(createComentarioUserDto(usuarioId,mc));
		return dto;
	}

	private ComentarioUserDto createComentarioUserDto(Long usuarioId,
			MiembroCens mc) {
		ComentarioUserDto dto = new ComentarioUserDto();
		dto.setFullname(mc.getApellido().toUpperCase()+", "+mc.getNombre()+" ("+mc.getDni()+")");
		dto.setUser_id(usuarioId);
		dto.setPicture("/css/midasUI-theme/images/user_blank_picture.png");
		dto.setIs_add_allowed(true);
		dto.setIs_edit_allowed(false);		
		return dto;
	}

}
