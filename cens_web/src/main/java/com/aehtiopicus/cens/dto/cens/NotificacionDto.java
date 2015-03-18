package com.aehtiopicus.cens.dto.cens;

import java.util.Set;

import com.aehtiopicus.cens.domain.entities.PerfilRol;

public class NotificacionDto {

	private PerfilRol perfilRol;
	
	private Set<CursoNotificacionDto> curso;

	public PerfilRol getPerfilRol() {
		return perfilRol;
	}

	public void setPerfilRol(PerfilRol perfilRol) {
		this.perfilRol = perfilRol;
	}

	public Set<CursoNotificacionDto> getCurso() {
		return curso;
	}

	public void setCurso(Set<CursoNotificacionDto> curso) {
		this.curso = curso;
	}
	
	
	
}
