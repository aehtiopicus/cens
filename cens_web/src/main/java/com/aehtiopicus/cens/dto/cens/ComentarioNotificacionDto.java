package com.aehtiopicus.cens.dto.cens;

import java.util.Set;

public class ComentarioNotificacionDto {

	private Set<CursoNotificacionDto> curso;

	public Set<CursoNotificacionDto> getCurso() {
		return curso;
	}

	public void setCurso(Set<CursoNotificacionDto> curso) {
		this.curso = curso;
	}

}
