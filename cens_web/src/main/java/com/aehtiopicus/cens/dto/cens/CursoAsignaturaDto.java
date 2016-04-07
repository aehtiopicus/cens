package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class CursoAsignaturaDto {

	private Long id;
	private String nombre;
	private int yearCurso;
	private List<AsignaturasDelCursoDto> asignaturasDelCursoDto;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getYearCurso() {
		return yearCurso;
	}
	public void setYearCurso(int yearCurso) {
		this.yearCurso = yearCurso;
	}
	public List<AsignaturasDelCursoDto> getAsignaturasDelCursoDto() {
		return asignaturasDelCursoDto;
	}
	public void setAsignaturasDelCursoDto(
			List<AsignaturasDelCursoDto> asignaturasDelCursoDto) {
		this.asignaturasDelCursoDto = asignaturasDelCursoDto;
	}
	
	
}
