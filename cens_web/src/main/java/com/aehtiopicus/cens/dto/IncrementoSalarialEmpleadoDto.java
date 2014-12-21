package com.aehtiopicus.cens.dto;


public class IncrementoSalarialEmpleadoDto{

	private Long empleadoId;
	private String empleado;
	private Integer legajo;
	private String estado;
	private String fechaSueldo;
	private String sueldoBasico;
	private String sueldoPresentismo;
	
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
	public Integer getLegajo() {
		return legajo;
	}
	public void setLegajo(Integer legajo) {
		this.legajo = legajo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaSueldo() {
		return fechaSueldo;
	}
	public void setFechaSueldo(String fechaSueldo) {
		this.fechaSueldo = fechaSueldo;
	}
	public String getSueldoBasico() {
		return sueldoBasico;
	}
	public void setSueldoBasico(String sueldoBasico) {
		this.sueldoBasico = sueldoBasico;
	}
	public String getSueldoPresentismo() {
		return sueldoPresentismo;
	}
	public void setSueldoPresentismo(String sueldoPresentismo) {
		this.sueldoPresentismo = sueldoPresentismo;
	}

}
