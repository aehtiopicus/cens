package com.aehtiopicus.cens.dto;

public class InformeConsolidadoAdicionalDetalleDto implements Comparable<InformeConsolidadoAdicionalDetalleDto>{

//	protected Long beneficioId;
	protected String beneficio;
	protected Double valor;
	
	public String getBeneficio() {
		return beneficio;
	}
	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@Override
	public int compareTo(InformeConsolidadoAdicionalDetalleDto o) {
		return this.beneficio.compareTo(o.getBeneficio());
	}
	
}
