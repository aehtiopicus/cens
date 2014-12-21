package com.aehtiopicus.cens.dto;


import org.hibernate.validator.constraints.NotBlank;


public class VacacionesDto {
	

	private Long vacacionesId;
	@NotBlank(message="Dato requerido")
	private String fechaFin;
	@NotBlank(message="Dato requerido")
	private String fechaInicio;
	private Long empleadoId;
	private String observaciones;
	
	public Long getVacacionesId() {
		return vacacionesId;
	}
	public void setVacacionesId(Long vacacionesId) {
		this.vacacionesId = vacacionesId;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Long getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(Long empleadoId) {
		this.empleadoId = empleadoId;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
