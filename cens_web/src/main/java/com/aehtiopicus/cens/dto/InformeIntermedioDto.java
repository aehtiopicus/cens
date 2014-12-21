package com.aehtiopicus.cens.dto;

public class InformeIntermedioDto {
	
	private String clienteNombre;
	private String gerenteNombre;
	private String periodo;
	private String estado;
	private Long informeId;
	   
    public String getClienteNombre() {
		return clienteNombre;
	}
	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}
	public String getGerenteNombre() {
		return gerenteNombre;
	}
	public void setGerenteNombre(String gerenteNombre) {
		this.gerenteNombre = gerenteNombre;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getInformeId() {
		return informeId;
	}
	public void setInformeId(Long informeId) {
		this.informeId = informeId;
	}
	
	
	

}
