package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcionResult;
import com.aehtiopicus.cens.dto.cens.AsignaturaInscripcionDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaInscripcionResultDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class InscripcionAlumnoCensMapper {

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

	

	public AsignaturaInscripcionResultDto convertAsignaturaInscripcionResultToDto(
			AsignaturaInscripcionResult inscribirAlumnos) {

		AsignaturaInscripcionResultDto airDto = Utils.getMapper().map(inscribirAlumnos, AsignaturaInscripcionResultDto.class);
		airDto.checkStatus();
		return airDto;
	}
}
