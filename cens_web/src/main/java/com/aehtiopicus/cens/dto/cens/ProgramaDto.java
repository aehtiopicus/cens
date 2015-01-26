package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;


public class ProgramaDto {

	private Long id;	
	private String nombre;
	private String descripcion;
	private Integer cantCartillas;
	private Long profesorId;
	private Long asignaturaId;
	private String programaAdjunto;
	private EstadoRevisionType estadoRevisionType;
	private String asignatura;
	
	
	
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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfesorId() {
		return profesorId;
	}
	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
	}
	public Long getAsignaturaId() {
		return asignaturaId;
	}
	public void setAsignaturaId(Long asignaturaId) {
		this.asignaturaId = asignaturaId;
	}
	public String getProgramaAdjunto() {
		return programaAdjunto;
	}
	public void setProgramaAdjunto(String programaAdjunto) {
		this.programaAdjunto = programaAdjunto;
	}
	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}
	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}
	public String getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	
	
}
