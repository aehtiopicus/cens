package com.aehtiopicus.cens.dto;


public class InformeMensualHeaderDto {

	protected Long informeMensualId;

	protected String periodo;
	protected String nombreCliente;
	protected String estado;
	
	public Long getInformeMensualId() {
		return informeMensualId;
	}
	public void setInformeMensualId(Long informeMensualId) {
		this.informeMensualId = informeMensualId;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
