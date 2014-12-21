package com.aehtiopicus.cens.dto;

import org.hibernate.validator.constraints.NotBlank;


public class SueldoDto {

	private Long sueldoId;
	@NotBlank(message="Dato requerido")
	private String basico;
	@NotBlank(message="Dato requerido")
	private String presentismo;
	@NotBlank(message="Dato requerido")
	private String fechaInicio;
	private Long empleadoId;
	private String empleado;
	
	public Long getSueldoId() {
		return sueldoId;
	}
	public void setSueldoId(Long sueldoId) {
		this.sueldoId = sueldoId;
	}
	public String getBasico() {
		return basico;
	}
	public void setBasico(String basico) {
		this.basico = basico;
	}
	public String getPresentismo() {
		return presentismo;
	}
	public void setPresentismo(String presentismo) {
		this.presentismo = presentismo;
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
	public String getEmpleado() {
		return empleado;
	}
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	
	
	
}
