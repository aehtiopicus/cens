package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.dto.cens.AsignaturasDelCursoDto;
import com.aehtiopicus.cens.dto.cens.CursoAsignaturaDto;
import com.aehtiopicus.cens.dto.cens.ProfesorAsignaturaDto;
import com.aehtiopicus.cens.dto.cens.ProfesorDto;
import com.aehtiopicus.cens.dto.cens.ProgramaDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class ProfesorCensMapper {

	@Autowired
	private ProgramaCensMapper programaCensMapper;
	
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
			List<Curso> cursoList,Profesor profesor, List<Programa> programaList) {
		ProfesorAsignaturaDto dto = new ProfesorAsignaturaDto();
		dto.setId(profesor.getId());
		dto.setCursoAsignatura(convertToCursoAsignatura(cursoList,programaList));
		return dto;
	}

	private List<CursoAsignaturaDto> convertToCursoAsignatura(
			List<Curso> cursoList, List<Programa> programaList) {
		List<CursoAsignaturaDto> caDtoList = new ArrayList<CursoAsignaturaDto>();
		if(CollectionUtils.isNotEmpty(cursoList)){
			CursoAsignaturaDto caDto;
			for(Curso c : cursoList){
				caDto= Utils.getMapper().map(c,CursoAsignaturaDto.class);
				if(CollectionUtils.isNotEmpty(programaList)){
					caDto.setAsignaturasDelCursoDto(convertToAsignaturaDelCursoDto(c.getAsignaturas(),programaList));
				}else{
					caDto.setAsignaturasDelCursoDto(convertToAsignaturaDelCursoDto(c.getAsignaturas()));
				}
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
	
	private List<AsignaturasDelCursoDto> convertToAsignaturaDelCursoDto(
			List<Asignatura> asignaturas, List<Programa> programaList) {
		List<AsignaturasDelCursoDto> adcDtoList = new ArrayList<AsignaturasDelCursoDto>();
		if(CollectionUtils.isNotEmpty(asignaturas)){
			AsignaturasDelCursoDto adcDto;
			for(Asignatura a : asignaturas){
				adcDto = Utils.getMapper().map(a, AsignaturasDelCursoDto.class);
				adcDto.setPrograma(convertToProgramaDeLaAsignatura(a,programaList));
				adcDtoList.add(adcDto);
			}
		}
		return adcDtoList;
				
	}

	private ProgramaDto convertToProgramaDeLaAsignatura(Asignatura a,
			List<Programa> programaList) {
		ProgramaDto dto = null;
		Iterator<Programa> pIterator = programaList.iterator();
		while(pIterator.hasNext()){
			Programa p = pIterator.next();
			if(p.getAsignatura().getId().equals(a.getId())){
				dto = programaCensMapper.convertProgramaToDto(p);
				pIterator.remove();
				break;
			}
		}
		
		return dto;
	}
}
