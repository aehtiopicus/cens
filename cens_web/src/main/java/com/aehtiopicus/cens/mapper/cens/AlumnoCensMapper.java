package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.dto.cens.AlumnoDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class AlumnoCensMapper {

	public AlumnoDto convertAlumnoToDto(Alumno alumno){
		return Utils.getMapper().map(alumno, AlumnoDto.class);
		
	}
	
	public Alumno convertAlumnoDtoToEntity(AlumnoDto alumnoDto){
		return Utils.getMapper().map(alumnoDto, Alumno.class);
		
	}

	public List<AlumnoDto> convertAlumnoListToDtoList(
			List<Alumno> alumnoList) {
		List<AlumnoDto> alumnoDtoList = new ArrayList<AlumnoDto>();
		if(CollectionUtils.isNotEmpty(alumnoList))
		for(Alumno a : alumnoList){
			alumnoDtoList.add(convertAlumnoToDto(a));
		}
		return alumnoDtoList;
	}
	
	public List<Alumno> convertAlumnoDtoListToEntityList(
			List<AlumnoDto> alumnoDtoList) {
		List<Alumno> alumnoList = new ArrayList<Alumno>();
		if(CollectionUtils.isNotEmpty(alumnoDtoList))
		for(AlumnoDto aDto : alumnoDtoList){
			alumnoList.add(convertAlumnoDtoToEntity(aDto));
		}
		return alumnoList;
	}

	
}
