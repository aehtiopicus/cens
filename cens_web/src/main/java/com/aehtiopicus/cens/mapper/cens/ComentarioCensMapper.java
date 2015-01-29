package com.aehtiopicus.cens.mapper.cens;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Preceptor;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.dto.cens.ComentarioDto;
import com.aehtiopicus.cens.dto.cens.ComentarioRequestDto;
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

	public ComentarioCens getDataForComentarioCens(
			ComentarioRequestDto comentarioRequestDto) {
		ComentarioCens cc = new ComentarioCens();
		
		switch(comentarioRequestDto.getUsuarioTipo()){
		case ADMINISTRADOR:
			break;
		case ALUMNO:
			Alumno alumno = new Alumno();
			alumno.setId(comentarioRequestDto.getUsuarioId());
			break;
		case ASESOR:
			Asesor a = new Asesor();
			a.setId(comentarioRequestDto.getUsuarioId());
			break;
		case PRECEPTOR:
			Profesor p = new Profesor();
			p.setId(comentarioRequestDto.getUsuarioId());
			break;
		case PROFESOR:
			Preceptor preceptor = new Preceptor();
			preceptor.setId(comentarioRequestDto.getUsuarioId());
			break;
		default:
			break;
		
		}
	
		cc.setBaja(false);
		cc.setComentario(comentarioRequestDto.getMensaje());
		cc.setFecha(new java.util.Date());
		ComentarioCens ccp = new ComentarioCens();
		ccp.setId(comentarioRequestDto.getParentId());
		cc.setParent(ccp);
		cc.setPrimero(cc.getParent()==null);
		cc.setTipoComentario(comentarioRequestDto.getTipoType());		
		cc.setTipoId(comentarioRequestDto.getTipoId());
		return cc;
	}

}
