package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class CursoAlumnoDashboardDto {

	private List<AsignaturaAlumnoDashboardDto> asignaturas;
	
	private Long id;
	private int yearCurso;
	private String nombre;
	
	public List<AsignaturaAlumnoDashboardDto> getAsignaturas() {
		return asignaturas;
	}
	public void setAsignaturas(List<AsignaturaAlumnoDashboardDto> asignaturas) {
		this.asignaturas = asignaturas;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getYearCurso() {
		return yearCurso;
	}
	public void setYearCurso(int yearCurso) {
		this.yearCurso = yearCurso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
