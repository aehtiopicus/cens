package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class AsesorDashboardDto {

	private Long id;
	private List<CursoAsesorDashboardDto> cursoDto;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<CursoAsesorDashboardDto> getCursoDto() {
		return cursoDto;
	}
	public void setCursoDto(List<CursoAsesorDashboardDto> cursoDto) {
		this.cursoDto = cursoDto;
	}
	
	
}
