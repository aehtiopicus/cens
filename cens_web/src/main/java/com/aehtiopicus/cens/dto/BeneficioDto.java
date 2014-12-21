package com.aehtiopicus.cens.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BeneficioDto {

	protected Long beneficioId;
	protected Long clienteId;
	
	
	@NotNull(message="Debe ingresar un título para el beneficio.")
	@Size(min=1, message="Debe ingresar un título para el beneficio.")
	protected String titulo;
	
	protected String descripcion;
	
	protected Float valor;

	protected String tipo; 
	
	protected String frecuencia;
	
	protected Boolean remunerativo;
	
	public Long getBeneficioId() {
		return beneficioId;
	}

	public void setBeneficioId(Long beneficioId) {
		this.beneficioId = beneficioId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Boolean getRemunerativo() {
		return remunerativo;
	}

	public void setRemunerativo(Boolean remunerativo) {
		this.remunerativo = remunerativo;
	}
	
	
}
