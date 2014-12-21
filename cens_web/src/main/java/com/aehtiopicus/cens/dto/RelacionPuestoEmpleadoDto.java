package com.aehtiopicus.cens.dto;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


public class RelacionPuestoEmpleadoDto {

	protected Long relacionLaboralId;
	protected Long clienteId;
	protected Long empleadoId;
	protected String nombreEmpleado;
	protected String razonSocialCliente;
	@NotBlank(message="Debe cargar fecha de ingreso")
	private String fechaInicio;
	
	
	
	protected String puestoNombre;
	@NotNull(message="Debe seleccionar un puesto.")
	protected Long puestoId;
	
	public Long getRelacionLaboralId() {
		return relacionLaboralId;
	}
	public void setRelacionLaboralId(Long relacionLaboralId) {
		this.relacionLaboralId = relacionLaboralId;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public Long getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(Long empleadoId) {
		this.empleadoId = empleadoId;
	}
	public String getNombreEmpleado() {
		return nombreEmpleado;
	}
	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}
	public String getRazonSocialCliente() {
		return razonSocialCliente;
	}
	public void setRazonSocialCliente(String razonSocialCliente) {
		this.razonSocialCliente = razonSocialCliente;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getPuestoNombre() {
		return puestoNombre;
	}
	public void setPuestoNombre(String puestoNombre) {
		this.puestoNombre = puestoNombre;
	}
	public Long getPuestoId() {
		return puestoId;
	}
	public void setPuestoId(Long puestoId) {
		this.puestoId = puestoId;
	}
	
	
}
