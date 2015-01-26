package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.dto.cens.AsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaAsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.CursoAsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.ProfesorAsesorDashboardDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class AsesosorCensMapper {

	public AsesorDashboardDto convertToAsesorDashboardDto(List<Curso> cursoList,Long asesorId) {
		AsesorDashboardDto dto = new AsesorDashboardDto();
		dto.setId(asesorId);
		dto.setCursoDto(createCursoAsesorDashboardDtoList(cursoList));
		return dto;
	}

	private List<CursoAsesorDashboardDto> createCursoAsesorDashboardDtoList(
			List<Curso> cursoList) {
		List<CursoAsesorDashboardDto> dtoList = null;
		if(CollectionUtils.isNotEmpty(cursoList)){
			CursoAsesorDashboardDto dto = null;		
			dtoList = new ArrayList<CursoAsesorDashboardDto>();
			for(Curso curso :cursoList ){
				dto = Utils.getMapper().map(curso,CursoAsesorDashboardDto.class);
				dto.setAsignaturas(createAsignaturaAsesorDashboardDtoList(curso.getAsignaturas()));
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private List<AsignaturaAsesorDashboardDto> createAsignaturaAsesorDashboardDtoList(
			List<Asignatura> asignaturas) {
		List<AsignaturaAsesorDashboardDto> dtoList = null;
		if(CollectionUtils.isNotEmpty(asignaturas)){
			AsignaturaAsesorDashboardDto dto;
			dtoList = new ArrayList<AsignaturaAsesorDashboardDto>();
			for(Asignatura asignatura : asignaturas){
				dto = Utils.getMapper().map(asignatura,AsignaturaAsesorDashboardDto.class);
				dto.setProfe(createProfesorAsesorDashboardDto(asignatura,true));
				dto.setProfeSuplente(createProfesorAsesorDashboardDto(asignatura,false));
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private ProfesorAsesorDashboardDto createProfesorAsesorDashboardDto(
			Asignatura asignatura,boolean titular) {
		ProfesorAsesorDashboardDto dto = null;
		Profesor p = titular ? asignatura.getProfesor() : asignatura.getProfesorSuplente();
		if(p!=null){
			dto =  Utils.getMapper().map(p.getMiembroCens(),ProfesorAsesorDashboardDto.class);
			dto.setId(p.getId());
		}
		return dto;

	}

}
