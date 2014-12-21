package com.aehtiopicus.cens.dto;

public class ExcelInformeConsolidadoDetalleBeneficioDto {

	protected Long id;
	protected Double valor;
	protected Long beneficioId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Long getBeneficioId() {
		return beneficioId;
	}
	public void setBeneficioId(Long beneficioId) {
		this.beneficioId = beneficioId;
	}
}
