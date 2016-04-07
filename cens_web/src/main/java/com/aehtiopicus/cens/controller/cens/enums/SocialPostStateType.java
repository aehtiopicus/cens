package com.aehtiopicus.cens.controller.cens.enums;

public enum SocialPostStateType {

	PUBLICADO("Publicado"),
	NO_PUBLICADO("No Publicado");
	
	private String estado;
	
	private SocialPostStateType(String estado){
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}
	
	
}

