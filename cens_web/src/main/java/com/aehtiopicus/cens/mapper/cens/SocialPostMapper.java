package com.aehtiopicus.cens.mapper.cens;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.SocialPost;
import com.aehtiopicus.cens.dto.cens.SocialPostDto;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.util.Utils;

@Component
public class SocialPostMapper {

	public String convertProgramaIntoSocialPostMessage(Programa p){
	
		return buildAsignaturaCurso(p);
	}
	
	private String buildAsignaturaCurso(Programa p){
		StringBuilder sb = new StringBuilder();
		sb.append("Programa ");
		sb.append(p.getNombre().toUpperCase());
		sb.append(" de la asignatura ");
		sb.append(p.getAsignatura().getNombre().toUpperCase());
		sb.append(" del Curso ");
		sb.append(p.getAsignatura().getCurso().getNombre().toUpperCase());
		sb.append("(");
		sb.append(p.getAsignatura().getCurso().getYearCurso());
		sb.append(") ");
		sb.append("Dictada por, Prof: ");
		if(p.getAsignatura().getProfesorSuplente()!=null){
			sb.append(p.getAsignatura().getProfesorSuplente().getMiembroCens().getApellido().toUpperCase());
		}else{
			sb.append(p.getAsignatura().getProfesor().getMiembroCens().getApellido().toUpperCase());
		}
		sb.append(" se encuentra disponible ");
		if(CollectionUtils.isNotEmpty(p.getMaterialDidactico())){
			sb.append(buildMaterialCurso(p.getMaterialDidactico()));
		}
		return sb.toString();
	}
	
	private String buildMaterialCurso(List<MaterialDidactico> mdList){

		int count = 0;
		for(MaterialDidactico md :mdList){
			if(md.getEstadoRevisionType().equals(EstadoRevisionType.ACEPTADO)){
				count++;
			}			
		}
		if(count>0){
			return " junto con "+(count)+" Cartilla/s";
		}
		return "";
	}

	public SocialPostDto mapEntityToDto(SocialPost sp) {
		return Utils.getMapper().map(sp, SocialPostDto.class);
	}
}
