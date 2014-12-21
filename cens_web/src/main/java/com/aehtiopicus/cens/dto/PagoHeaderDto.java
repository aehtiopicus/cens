package com.aehtiopicus.cens.dto;

import org.hibernate.validator.constraints.NotBlank;


public class PagoHeaderDto {

	@NotBlank(message="Campo Requerido")
	protected String periodo;
	@NotBlank(message="Campo Requerido")
	protected String concepto;
	protected String fechaAcr;
	
	
	public String getFechaAcr() {
		return fechaAcr;
	}
	public void setFechaAcr(String fechaAcr) {
		this.fechaAcr = fechaAcr;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
}
