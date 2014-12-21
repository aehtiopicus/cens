package com.aehtiopicus.cens.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;


public class RelacionLaboralDto {

	protected Long relacionLaboralId;

	@NotNull(message="Debe seleccionar un cliente.")
	protected Long clienteId;
	protected Long empleadoId;

	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String mailEmpresa;
	
	protected String nombreEmpleado;
	protected Integer legajoEmpleado;
	
	protected String razonSocialCliente;
	
	@NotBlank(message="Debe ingresar una fecha de inicio.")
	protected String fechaInicio;
	protected String fechaFin;
	
	protected String puestoNombre;

	protected boolean accionDesasignar = true;
	protected boolean accionCambiarPuesto = true;
	protected boolean accionHistorial = true;
	
	public boolean isAccionDesasignar() {
		return accionDesasignar;
	}
	public void setAccionDesasignar(boolean accionDesasignar) {
		this.accionDesasignar = accionDesasignar;
	}
	public boolean isAccionCambiarPuesto() {
		return accionCambiarPuesto;
	}
	public void setAccionCambiarPuesto(boolean accionCambiarPuesto) {
		this.accionCambiarPuesto = accionCambiarPuesto;
	}
	public boolean isAccionHistorial() {
		return accionHistorial;
	}
	public void setAccionHistorial(boolean accionHistorial) {
		this.accionHistorial = accionHistorial;
	}
	
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
	public Integer getLegajoEmpleado() {
		return legajoEmpleado;
	}
	public void setLegajoEmpleado(Integer legajoEmpleado) {
		this.legajoEmpleado = legajoEmpleado;
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
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
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
	public String getMailEmpresa() {
		return mailEmpresa;
	}
	public void setMailEmpresa(String mailEmpresa) {
		this.mailEmpresa = mailEmpresa;
	}
}
