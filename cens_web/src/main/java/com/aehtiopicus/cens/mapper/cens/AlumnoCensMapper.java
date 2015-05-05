package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.dto.cens.AlumnoDto;
import com.aehtiopicus.cens.dto.cens.AlumnoInscripcionDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaInscripcionDto;
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

	public AsignaturaInscripcion convertAsignaturaInscripcionDtoToEntityWrapper(
			AsignaturaInscripcionDto asignaturaInscripcionDto) {
		AsignaturaInscripcion ai = new AsignaturaInscripcion();
		Asignatura a = new Asignatura();		
		List<Alumno> alumnos = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(asignaturaInscripcionDto.getAlumnoIds())){
			for(Long alumnoId : asignaturaInscripcionDto.getAlumnoIds()){
				Alumno al = new Alumno();
				al.setId(alumnoId);
				alumnos.add(al);
			}
		}
		
		ai.setAlumnos(alumnos);
		ai.setAsignatura(a);
		return ai;		
	}

	public List<AlumnoInscripcionDto> mapInscripcionOk(
			AsignaturaInscripcionDto asignaturaInscripcionDto) {
		
		List<AlumnoInscripcionDto> aiDtoList = new ArrayList<AlumnoInscripcionDto>();
				
		for(Long alumnoId : asignaturaInscripcionDto.getAlumnoIds()){
			AlumnoInscripcionDto aiDto = new AlumnoInscripcionDto();
			aiDto.setMessage("ok");
			aiDto.setStatus(true);
			aiDto.setAlumnoId(alumnoId);
			aiDtoList.add(aiDto);
		}
		return aiDtoList;
	}
	
	
	public List<AlumnoInscripcionDto> mapInscripcionError(Long alumnoId,String mesage) {
		
		List<AlumnoInscripcionDto> aiDtoList = new ArrayList<AlumnoInscripcionDto>();
				
		
			AlumnoInscripcionDto aiDto = new AlumnoInscripcionDto();
			aiDto.setMessage("Error: "+mesage);
			aiDto.setStatus(false);
			aiDto.setAlumnoId(alumnoId);
			aiDtoList.add(aiDto);
		
		return aiDtoList;
	}
}
