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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CursoAlumnoDashboardDto other = (CursoAlumnoDashboardDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
