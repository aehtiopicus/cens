package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class AsignaturaInscripcionDto {

	private Long asignaturaId;
	
	private List<Long> alumnoIds;

	public Long getAsignaturaId() {
		return asignaturaId;
	}

	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}

	public List<Long> getAlumnoIds() {
		return alumnoIds;
	}

	public void setAlumnoIds(List<Long> alumnoIds) {
		this.alumnoIds = alumnoIds;
	}

	
	
	
}
