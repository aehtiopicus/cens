package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class ProgramaAsesorDashboardDto {
	
	private Long id;
	private EstadoRevisionType estadoRevisionType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}

	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}	
	
	
}
