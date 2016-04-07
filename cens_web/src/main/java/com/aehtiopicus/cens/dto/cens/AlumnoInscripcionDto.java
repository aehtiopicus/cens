package com.aehtiopicus.cens.dto.cens;

import java.util.Map;

public class AlumnoInscripcionDto {

	private Long alumnoId;
	
	private boolean status;
	
	private Map<String,Object> message;

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Map<String, Object> getMessage() {
		return message;
	}

	public void setMessage(Map<String, Object> message) {
		this.message = message;
	}

	
	
	
	
}
