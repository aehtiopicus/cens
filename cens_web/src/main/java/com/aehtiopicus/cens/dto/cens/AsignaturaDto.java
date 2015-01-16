package com.aehtiopicus.cens.dto.cens;


public class AsignaturaDto {
	
	private Long id;	
	private String nombre;
	private String modalidad;	
	private String horarios;
	private AsignaturaCursoDto curso;
	private AsignaturaProfesorDto profesor;
	private AsignaturaProfesorDto profesorSuplente;
	private boolean vigente;
	
	
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
	public AsignaturaCursoDto getCurso() {
		return curso;
	}
	public void setCurso(AsignaturaCursoDto curso) {
		this.curso = curso;
	}
	public AsignaturaProfesorDto getProfesor() {
		return profesor;
	}
	public void setProfesor(AsignaturaProfesorDto profesor) {
		this.profesor = profesor;
	}
	public AsignaturaProfesorDto getProfesorSuplente() {
		return profesorSuplente;
	}
	public void setProfesorSuplente(AsignaturaProfesorDto profesorSuplente) {
		this.profesorSuplente = profesorSuplente;
	}
	public boolean isVigente() {
		return vigente;
	}
	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}
	
	
	
}
