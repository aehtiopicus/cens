package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.dto.cens.ProgramaDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class ProgramaCensMapper {

	public ProgramaDto convertProgramaToDto(Programa programa){
		ProgramaDto dto =Utils.getMapper().map(programa, ProgramaDto.class);
		dto.setProfesorId(programa.getProfesor().getId());
		dto.setAsignaturaId(programa.getAsignatura().getId());
		dto.setProgramaAdjunto(programa.getFileInfo()!=null ? programa.getFileInfo().getFileName() : "");
		dto.setEstadoRevisionType(programa.getEstadoRevisionType());
		dto.setAsignatura(programa.getAsignatura().getNombre() +" ("+ programa.getAsignatura().getCurso().getNombre()+" "+programa.getAsignatura().getCurso().getYearCurso()+")");
		return dto;
		
	}
	
	public Programa convertProgramaDtoToEntity(ProgramaDto programaDto){
		Programa programa =Utils.getMapper().map(programaDto, Programa.class);
		
		Profesor p = new Profesor();
		p.setId(programaDto.getProfesorId());
		programa.setProfesor(p);
		
		Asignatura a = new Asignatura();
		a.setId(programaDto.getAsignaturaId());
		programa.setAsignatura(a);
		return programa;
		
	}

	public List<ProgramaDto> convertProgramaListToDtoList(
			List<Programa> ProgramaList) {
		List<ProgramaDto> ProgramaDtoList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(ProgramaList))
		for(Programa p : ProgramaList){
			ProgramaDtoList.add(convertProgramaToDto(p));
		}
		return ProgramaDtoList;
	}
	
	public List<Programa> convertProgramaDtoListToEntityList(
			List<ProgramaDto> ProgramaDtoList) {
		List<Programa> ProgramaList = new ArrayList<Programa>();
		if(CollectionUtils.isNotEmpty(ProgramaDtoList))
		for(ProgramaDto pDto : ProgramaDtoList){
			ProgramaList.add(convertProgramaDtoToEntity(pDto));
		}
		return ProgramaList;
	}

	
}
