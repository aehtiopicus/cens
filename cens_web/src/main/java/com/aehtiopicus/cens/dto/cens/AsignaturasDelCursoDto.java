package com.aehtiopicus.cens.dto.cens;

public class AsignaturasDelCursoDto {

	private Long id;	
	private String nombre;
	private String modalidad;	
	private String horarios;
	private ProgramaDto programa;
	
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
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public String getHorarios() {
		return horarios;
	}
	public void setHorarios(String horarios) {
		this.horarios = horarios;
	}
	public ProgramaDto getPrograma() {
		return programa;
	}
	public void setPrograma(ProgramaDto programa) {
		this.programa = programa;
	}
	
	
}
