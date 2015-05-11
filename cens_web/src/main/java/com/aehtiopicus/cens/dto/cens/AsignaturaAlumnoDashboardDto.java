package com.aehtiopicus.cens.dto.cens;


public class AsignaturaAlumnoDashboardDto {

	private Long id;	

	private String nombre;

	private String modalidad;
		
	private String horarios;
		
	private ProfesorDto profesor;
	
	private ProfesorDto profesorSuplente;
	
	private ProgramaDto programaDto;

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

	public ProfesorDto getProfesor() {
		return profesor;
	}

	public void setProfesor(ProfesorDto profesor) {
		this.profesor = profesor;
	}

	public ProfesorDto getProfesorSuplente() {
		return profesorSuplente;
	}

	public void setProfesorSuplente(ProfesorDto profesorSuplente) {
		this.profesorSuplente = profesorSuplente;
	}

	public ProgramaDto getProgramaDto() {
		return programaDto;
	}

	public void setProgramaDto(ProgramaDto programaDto) {
		this.programaDto = programaDto;
	}
	
	
}
