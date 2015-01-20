package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class ProfesorAsignaturaDto {

	private Long id;
	private List<CursoAsignaturaDto> cursoAsignatura;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<CursoAsignaturaDto> getCursoAsignatura() {
		return cursoAsignatura;
	}
	public void setCursoAsignatura(List<CursoAsignaturaDto> cursoAsignatura) {
		this.cursoAsignatura = cursoAsignatura;
	}
	
	
}
