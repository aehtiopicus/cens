package com.aehtiopicus.cens.domain.entities;

import java.util.List;

public class AsignaturaInscripcionResult {

	private Long asignaturaId;
	
	private List<AlumnoInscripcion> alumnosInscriptos;
	
	private boolean inscripcionStatus = false;

	public Long getAsignaturaId() {
		return asignaturaId;
	}

	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}

	public List<AlumnoInscripcion> getAlumnosInscriptos() {
		return alumnosInscriptos;
	}

	public void setAlumnosInscriptos(List<AlumnoInscripcion> alumnosInscriptos) {
		this.alumnosInscriptos = alumnosInscriptos;
	}

	public boolean isInscripcionStatus() {
		return inscripcionStatus;
	}

	public void setInscripcionStatus(boolean inscripcionStatus) {
		this.inscripcionStatus = inscripcionStatus;
	}
	
	
}
