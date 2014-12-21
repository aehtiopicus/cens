package com.aehtiopicus.cens.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class BeneficioClienteDto implements Comparable<BeneficioClienteDto> {

	protected Long beneficioClienteId;
	
	protected Long beneficioId;
	protected Long clienteId;
	
	protected String titulo;
	
	protected String descripcion;
	
	@NotEmpty(message="Debe cargar un valor numérico.")
	protected String valor;

	@NotEmpty(message="Debe seleccionar un tipo")
	protected String tipo; 
	
	protected Boolean habilitado;
	
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getBeneficioClienteId() {
		return beneficioClienteId;
	}

	public void setBeneficioClienteId(Long beneficioClienteId) {
		this.beneficioClienteId = beneficioClienteId;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	@Override
	public int compareTo(BeneficioClienteDto o) {
		return this.getTitulo().compareTo(o.getTitulo());
	}
}
