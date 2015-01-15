package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.dto.cens.ProfesorDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class ProfesorCensMapper {

	public ProfesorDto convertProfesorToDto(Profesor profesor){
		return Utils.getMapper().map(profesor, ProfesorDto.class);
		
	}
	
	public Profesor convertProfesorDtoToEntity(ProfesorDto ProfesorDto){
		return Utils.getMapper().map(ProfesorDto, Profesor.class);
		
	}

	public List<ProfesorDto> convertProfesorListToDtoList(
			List<Profesor> profesorList) {
		List<ProfesorDto> profesorDtoList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(profesorList))
		for(Profesor p : profesorList){
			profesorDtoList.add(convertProfesorToDto(p));
		}
		return profesorDtoList;
	}
	
	public List<Profesor> convertProfesorDtoListToEntityList(
			List<ProfesorDto> profesorDtoList) {
		List<Profesor> profesorList = new ArrayList<Profesor>();
		if(CollectionUtils.isNotEmpty(profesorDtoList))
		for(ProfesorDto pDto : profesorDtoList){
			profesorList.add(convertProfesorDtoToEntity(pDto));
		}
		return profesorList;
	}
}
