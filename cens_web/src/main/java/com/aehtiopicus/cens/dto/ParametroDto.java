package com.aehtiopicus.cens.dto;

import org.hibernate.validator.constraints.NotBlank;

public class ParametroDto implements Comparable<ParametroDto>{
	
	protected Long entityId;
	
	@NotBlank(message="Nombre no puede estar vacío")
	protected String parametro;
	
	protected String descripcion;
	
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public String getParametro() {
		return parametro;
	}
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public int compareTo(ParametroDto o) {
		return this.getParametro().compareTo(o.getParametro());
	}
}
