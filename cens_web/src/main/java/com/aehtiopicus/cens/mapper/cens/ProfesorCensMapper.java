package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.dto.cens.AsignaturasDelCursoDto;
import com.aehtiopicus.cens.dto.cens.CursoAsignaturaDto;
import com.aehtiopicus.cens.dto.cens.ProfesorAsignaturaDto;
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

	public ProfesorAsignaturaDto convertCursoListIntoProfesorAsignaturaDto(
			List<Curso> cursoList,Profesor profesor) {
		ProfesorAsignaturaDto dto = new ProfesorAsignaturaDto();
		dto.setId(profesor.getId());
		dto.setCursoAsignatura(convertToCursoAsignatura(cursoList));
		return dto;
	}

	private List<CursoAsignaturaDto> convertToCursoAsignatura(
			List<Curso> cursoList) {
		List<CursoAsignaturaDto> caDtoList = new ArrayList<CursoAsignaturaDto>();
		if(CollectionUtils.isNotEmpty(cursoList)){
			CursoAsignaturaDto caDto;
			for(Curso c : cursoList){
				caDto= Utils.getMapper().map(c,CursoAsignaturaDto.class);
				caDto.setAsignaturasDelCursoDto(convertToAsignaturaDelCursoDto(c.getAsignaturas()));
				caDtoList.add(caDto);
			}
		}
		return caDtoList;
	}

	private List<AsignaturasDelCursoDto> convertToAsignaturaDelCursoDto(
			List<Asignatura> asignaturas) {
		List<AsignaturasDelCursoDto> adcDtoList = new ArrayList<AsignaturasDelCursoDto>();
		if(CollectionUtils.isNotEmpty(asignaturas)){
			AsignaturasDelCursoDto adcDto;
			for(Asignatura a : asignaturas){
				adcDto = Utils.getMapper().map(a, AsignaturasDelCursoDto.class);
				adcDtoList.add(adcDto);
			}
		}
		return adcDtoList;
				
	}
}
