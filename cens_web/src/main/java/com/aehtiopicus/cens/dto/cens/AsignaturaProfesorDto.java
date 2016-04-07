package com.aehtiopicus.cens.dto.cens;

public class AsignaturaProfesorDto {
	
	private Long id;
	private AsignaturaMiembroCensDto miembroCens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AsignaturaMiembroCensDto getMiembroCens() {
		return miembroCens;
	}

	public void setMiembroCens(AsignaturaMiembroCensDto miembroCens) {
		this.miembroCens = miembroCens;
	}
	
	
}
