package com.aehtiopicus.cens.dto.cens;

public class AsignaturaProgramaDto {

	private String nombre;
	private String descripcion;
	private Integer cantCartillas;
	private Long profesorId;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getCantCartillas() {
		return cantCartillas;
	}
	public void setCantCartillas(Integer cantCartillas) {
		this.cantCartillas = cantCartillas;
	}
	public Long getProfesorId() {
		return profesorId;
	}
	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
	}
	
	
	
}
