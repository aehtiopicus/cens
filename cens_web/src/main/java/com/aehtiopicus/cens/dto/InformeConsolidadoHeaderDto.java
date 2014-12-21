package com.aehtiopicus.cens.dto;


public class InformeConsolidadoHeaderDto {

	protected Long informeConsolidadoId;

	protected String periodo;
	protected String estado;
	protected String tipo;
	
	public Long getInformeConsolidadoId() {
		return informeConsolidadoId;
	}
	public void setInformeConsolidadoId(Long informeConsolidadoId) {
		this.informeConsolidadoId = informeConsolidadoId;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
