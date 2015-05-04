package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class AsignaturaInscripcionResultDto {

	private Long asignaturaId;
	
	private List<AlumnoInscripcionDto> alumnosInscriptos;
	
	private boolean inscripcionStatus = false;

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
	
	
}
