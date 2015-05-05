package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class AsignaturaInscripcionResultDto {

	private Long asignaturaId;
	
	private List<AlumnoInscripcionDto> alumnosInscriptos;
	
	private boolean inscripcionStatus = false;
	
	private boolean inscripcionCompleteFailure = false;

	public Long getAsignaturaId() {
		return asignaturaId;
	}

	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}

	public List<AlumnoInscripcionDto> getAlumnosInscriptos() {
		return alumnosInscriptos;
	}

	public void setAlumnosInscriptos(List<AlumnoInscripcionDto> alumnosInscriptos) {
		this.alumnosInscriptos = alumnosInscriptos;
	}

	public boolean isInscripcionStatus() {
		return inscripcionStatus;
	}

	public void setInscripcionStatus(boolean inscripcionStatus) {
		this.inscripcionStatus = inscripcionStatus;
	}

	public void checkStatus() {
		inscripcionStatus = true;
		int count = 0;
		for(AlumnoInscripcionDto ai :alumnosInscriptos){
			if(!ai.isStatus()){
				inscripcionStatus = false;
				count++;
			}
		}
		if(count == alumnosInscriptos.size()){
			inscripcionCompleteFailure = true;
		}
		
	}

	public boolean isInscripcionCompleteFailure() {
		return inscripcionCompleteFailure;
	}

	public void setInscripcionCompleteFailure(boolean inscripcionCompleteFailure) {
		this.inscripcionCompleteFailure = inscripcionCompleteFailure;
	}
	
	
	
	
}
