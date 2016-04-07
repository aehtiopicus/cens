package com.aehtiopicus.cens.dto.cens;

import java.util.List;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class ProgramaAsesorDashboardDto {
	
	private Long id;
	private EstadoRevisionType estadoRevisionType;
	private List<MaterialDidacticoDashboardDto> materialDidactico;
	
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

	public List<MaterialDidacticoDashboardDto> getMaterialDidactico() {
		return materialDidactico;
	}

	public void setMaterialDidactico(
			List<MaterialDidacticoDashboardDto> materialDidactico) {
		this.materialDidactico = materialDidactico;
	}	
	
	
}
