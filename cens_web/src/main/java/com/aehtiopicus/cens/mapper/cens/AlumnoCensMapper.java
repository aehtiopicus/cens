package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.dto.cens.AlumnoDashboardDto;
import com.aehtiopicus.cens.dto.cens.AlumnoDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaAlumnoDashboardDto;
import com.aehtiopicus.cens.dto.cens.CursoAlumnoDashboardDto;
import com.aehtiopicus.cens.dto.cens.MaterialDidacticoDto;
import com.aehtiopicus.cens.dto.cens.ProgramaDto;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.util.Utils;

@Component
public class AlumnoCensMapper {

	public AlumnoDto convertAlumnoToDto(Alumno alumno) {
		return Utils.getMapper().map(alumno, AlumnoDto.class);

	}

	public Alumno convertAlumnoDtoToEntity(AlumnoDto alumnoDto) {
		return Utils.getMapper().map(alumnoDto, Alumno.class);

	}

	public List<AlumnoDto> convertAlumnoListToDtoList(List<Alumno> alumnoList) {
		List<AlumnoDto> alumnoDtoList = new ArrayList<AlumnoDto>();
		if (CollectionUtils.isNotEmpty(alumnoList))
			for (Alumno a : alumnoList) {
				alumnoDtoList.add(convertAlumnoToDto(a));
			}
		return alumnoDtoList;
	}

	public List<Alumno> convertAlumnoDtoListToEntityList(
			List<AlumnoDto> alumnoDtoList) {
		List<Alumno> alumnoList = new ArrayList<Alumno>();
		if (CollectionUtils.isNotEmpty(alumnoDtoList))
			for (AlumnoDto aDto : alumnoDtoList) {
				alumnoList.add(convertAlumnoDtoToEntity(aDto));
			}
		return alumnoList;
	}

	public AlumnoDashboardDto convertToDashboardDto(
			Map<Asignatura, Programa> asignaturaProgramaMap) {
		AlumnoDashboardDto adDto = new AlumnoDashboardDto();
		if (!asignaturaProgramaMap.isEmpty()) {
			Set<CursoAlumnoDashboardDto> cursoSet = new HashSet<CursoAlumnoDashboardDto>();

			for (Entry<Asignatura, Programa> apEntry : asignaturaProgramaMap
					.entrySet()) {
				CursoAlumnoDashboardDto cDto = createCursoAlumnoDashboardDto(apEntry
						.getKey().getCurso());

				if (!cursoSet.contains(cDto)) {
					cDto.setAsignaturas(new ArrayList<AsignaturaAlumnoDashboardDto>());
					cursoSet.add(cDto);
				} else {
					for (CursoAlumnoDashboardDto aux : cursoSet) {
						if (aux.equals(cDto)) {
							cDto = aux;
							break;
						}
					}
				}
				AsignaturaAlumnoDashboardDto aDto = createAsignaturaAlumnoDashboardDto(apEntry
						.getKey());
				if (apEntry.getValue() != null && apEntry.getValue().getEstadoRevisionType().equals(EstadoRevisionType.ACEPTADO)) {					
					aDto.setProgramaDto(createProgramaDto(apEntry.getValue()));
				} else {
					aDto.setProgramaDto(new ProgramaDto());
				}
				cDto.getAsignaturas().add(aDto);

			}
			adDto.setCursos(cursoSet);
		}

		return adDto;

	}

	private ProgramaDto createProgramaDto(Programa p) {
		ProgramaDto pDto = Utils.getMapper().map(p, ProgramaDto.class);
		pDto.setAsignatura(null);
		pDto.setProgramaAdjunto(p.getFileInfo()!=null ? p.getFileInfo().getFileName() : null);
		if(p.getMaterialDidactico()!=null){
			pDto.setMaterialDidactico(new ArrayList<MaterialDidacticoDto>());
			for(MaterialDidactico md : p.getMaterialDidactico()){
				if(md.getEstadoRevisionType().equals(EstadoRevisionType.ACEPTADO)){
					MaterialDidacticoDto mDto = Utils.getMapper().map(md, MaterialDidacticoDto.class);				
					mDto.setCartillaAdjunta(md.getFileInfo()!=null ? md.getFileInfo().getFileName() : null);				
					pDto.getMaterialDidactico().add(mDto);
				}
			}
		}
		return pDto;
	}

	private AsignaturaAlumnoDashboardDto createAsignaturaAlumnoDashboardDto(
			Asignatura a) {
		AsignaturaAlumnoDashboardDto aDto = Utils.getMapper().map(a, AsignaturaAlumnoDashboardDto.class);
		
		if(aDto.getProfesorSuplente() !=null){
			aDto.getProfesorSuplente().getMiembroCens().setUsuario(null);
		}else if(aDto.getProfesor()!=null){
			aDto.getProfesor().getMiembroCens().setUsuario(null);
		}
		return aDto;
	}

	private CursoAlumnoDashboardDto createCursoAlumnoDashboardDto(Curso c) {

		return Utils.getMapper().map(c, CursoAlumnoDashboardDto.class);

	}

}
