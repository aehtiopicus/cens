package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.dto.cens.AsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.AsesorDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaAsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.CursoAsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.MaterialDidacticoDashboardDto;
import com.aehtiopicus.cens.dto.cens.ProfesorAsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.ProgramaAsesorDashboardDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class AsesosorCensMapper {

	@Cacheable(value="asesorDashboardMapper",key="#asesorId")
	public AsesorDashboardDto convertToAsesorDashboardDto(List<Curso> cursoList,Long asesorId, List<Programa> programas) {
		AsesorDashboardDto dto = new AsesorDashboardDto();
		dto.setId(asesorId);
		dto.setCursoDto(createCursoAsesorDashboardDtoList(cursoList,programas));
		return dto;
	}

	private List<CursoAsesorDashboardDto> createCursoAsesorDashboardDtoList(
			List<Curso> cursoList,List<Programa> programaList) {
		List<CursoAsesorDashboardDto> dtoList = null;
		if(CollectionUtils.isNotEmpty(cursoList)){
			CursoAsesorDashboardDto dto = null;		
			dtoList = new ArrayList<CursoAsesorDashboardDto>();
			for(Curso curso :cursoList ){
				dto = Utils.getMapper().map(curso,CursoAsesorDashboardDto.class);
				dto.setAsignaturas(createAsignaturaAsesorDashboardDtoList(curso.getAsignaturas(),programaList));
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private List<AsignaturaAsesorDashboardDto> createAsignaturaAsesorDashboardDtoList(
			List<Asignatura> asignaturas,List<Programa> programaList) {
		List<AsignaturaAsesorDashboardDto> dtoList = null;
		if(CollectionUtils.isNotEmpty(asignaturas)){
			AsignaturaAsesorDashboardDto dto;
			dtoList = new ArrayList<AsignaturaAsesorDashboardDto>();
			for(Asignatura asignatura : asignaturas){
				dto = Utils.getMapper().map(asignatura,AsignaturaAsesorDashboardDto.class);
				dto.setProfe(createProfesorAsesorDashboardDto(asignatura,true));
				dto.setProfeSuplente(createProfesorAsesorDashboardDto(asignatura,false));
				dto.setPrograma(createProgramaAsesorDashboardDto(asignatura,programaList));
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	private ProgramaAsesorDashboardDto createProgramaAsesorDashboardDto(Asignatura a, List<Programa> programaList){		
		ProgramaAsesorDashboardDto dto =null;
		if(CollectionUtils.isNotEmpty(programaList)){
			Iterator<Programa> programaIterator = programaList.iterator();
			while(programaIterator.hasNext()){
				Programa p = programaIterator.next();
				if(p.getAsignatura().getId().equals(a.getId())){
					dto = new ProgramaAsesorDashboardDto();
					dto.setId(p.getId());
					dto.setEstadoRevisionType(p.getEstadoRevisionType());
					dto.setMaterialDidactico(createMaterialDidacticoDashboardList(p.getMaterialDidactico()));
					programaIterator.remove();
				}
			}
			
		}
		
		return dto;
	}

	private List<MaterialDidacticoDashboardDto> createMaterialDidacticoDashboardList(
			List<MaterialDidactico> materialDidactico) {
		List<MaterialDidacticoDashboardDto> resultList = null;
		if(CollectionUtils.isNotEmpty(materialDidactico)){
			resultList = new ArrayList<MaterialDidacticoDashboardDto>();
			for(MaterialDidactico md : materialDidactico){
				resultList.add(Utils.getMapper().map(md, MaterialDidacticoDashboardDto.class));
			}
		}
		return resultList;
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

	public AsesorDto convertAsesorToDto(Asesor asesor){
		return Utils.getMapper().map(asesor, AsesorDto.class);
		
	}
	
	public Asesor convertAsesorDtoToEntity(AsesorDto asesorDto){
		return Utils.getMapper().map(asesorDto, Asesor.class);
		
	}

	public List<AsesorDto> convertAsesorListToDtoList(
			List<Asesor> asesorList) {
		List<AsesorDto> asesorDtoList = new ArrayList<AsesorDto>();
		if(CollectionUtils.isNotEmpty(asesorList))
		for(Asesor a : asesorList){
			asesorDtoList.add(convertAsesorToDto(a));
		}
		return asesorDtoList;
	}
	
	public List<Asesor> convertAsesorDtoListToEntityList(
			List<AsesorDto> asesorDtoList) {
		List<Asesor> asesorList = new ArrayList<Asesor>();
		if(CollectionUtils.isNotEmpty(asesorDtoList))
		for(AsesorDto aDto : asesorDtoList){
			asesorList.add(convertAsesorDtoToEntity(aDto));
		}
		return asesorList;
	}

}
