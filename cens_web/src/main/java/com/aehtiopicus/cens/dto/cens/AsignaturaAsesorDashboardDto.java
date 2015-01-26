package com.aehtiopicus.cens.dto.cens;

public class AsignaturaAsesorDashboardDto {

	private Long id;	
	private String nombre;
	private String modalidad;	
	private String horarios;
	private ProfesorAsesorDashboardDto profe;
	private ProfesorAsesorDashboardDto profeSuplente;

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
	public ProfesorAsesorDashboardDto getProfe() {
		return profe;
	}
	public void setProfe(ProfesorAsesorDashboardDto profe) {
		this.profe = profe;
	}
	public ProfesorAsesorDashboardDto getProfeSuplente() {
		return profeSuplente;
	}
	public void setProfeSuplente(ProfesorAsesorDashboardDto profeSuplente) {
		this.profeSuplente = profeSuplente;
	}
	
	
	
}
