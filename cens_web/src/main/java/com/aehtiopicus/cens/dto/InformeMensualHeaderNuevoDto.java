package com.aehtiopicus.cens.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


public class InformeMensualHeaderNuevoDto {

	protected Long informeMensualId;

	@NotBlank(message="Debe seleccionar un periodo.")
	protected String periodo;
	@NotNull(message="Debe seleccionar un cliente.")
	protected Long clienteId;
	
	protected Boolean usarInformeAnterior = true;
	
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
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public Boolean getUsarInformeAnterior() {
		return usarInformeAnterior;
	}
	public void setUsarInformeAnterior(Boolean usarInformeAnterior) {
		this.usarInformeAnterior = usarInformeAnterior;
	}
}
