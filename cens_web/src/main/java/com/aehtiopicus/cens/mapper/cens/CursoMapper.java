package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.dto.cens.CursoDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class CursoMapper {

	public CursoDto convertCursoToDto(Curso curso){
		return Utils.getMapper().map(curso, CursoDto.class);
		
	}
	
	public Curso convertCursoDtoToEntity(CursoDto CursoDto){
		return Utils.getMapper().map(CursoDto, Curso.class);
		
	}

	public List<CursoDto> convertCursoListToDtoList(
			List<Curso> cursoList) {
		List<CursoDto> cursoDtoList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(cursoList))
		for(Curso c : cursoList){
			cursoDtoList.add(convertCursoToDto(c));
		}
		return cursoDtoList;
	}
	
	public List<Curso> convertCursoDtoListToEntityList(
			List<CursoDto> cursoDtoList) {
		List<Curso> cursoList = new ArrayList<Curso>();
		if(CollectionUtils.isNotEmpty(cursoDtoList))
		for(CursoDto cDto : cursoDtoList){
			cursoList.add(convertCursoDtoToEntity(cDto));
		}
		return cursoList;
	}
}
