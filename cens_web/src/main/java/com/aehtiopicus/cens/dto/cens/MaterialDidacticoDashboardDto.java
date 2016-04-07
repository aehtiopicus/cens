package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class MaterialDidacticoDashboardDto {

	private Long id;
	private int nro;
	private EstadoRevisionType estadoRevisionType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNro() {
		return nro;
	}
	public void setNro(int nro) {
		this.nro = nro;
	}
	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}
	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}
	
	
}
