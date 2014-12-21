package com.aehtiopicus.cens.dto;

import javax.validation.constraints.NotNull;

public class IncrementoSalarialDto {

	@NotNull(message="Debe seleccionar un cliente.")
	protected Long clienteId;
	
	protected String tipoIncremento;
	
	protected String incrementoBasico = "0.00";
	
	protected String incrementoPresentismo = "0.00";
	
	protected String fechaInicio;

	
	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getIncrementoBasico() {
		return incrementoBasico;
	}

	public void setIncrementoBasico(String incrementoBasico) {
		this.incrementoBasico = incrementoBasico;
	}

	public String getIncrementoPresentismo() {
		return incrementoPresentismo;
	}

	public void setIncrementoPresentismo(
			String incrementoPresentismo) {
		this.incrementoPresentismo = incrementoPresentismo;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getTipoIncremento() {
		return tipoIncremento;
	}

	public void setTipoIncremento(String tipoIncremento) {
		this.tipoIncremento = tipoIncremento;
	}
}
