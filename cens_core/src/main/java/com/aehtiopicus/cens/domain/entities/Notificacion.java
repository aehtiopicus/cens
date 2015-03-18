package com.aehtiopicus.cens.domain.entities;

import java.util.Map;

public class Notificacion {

	private Map<String,Object> data;
	
	private PerfilRol perfilRol;
	
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public PerfilRol getPerfilRol() {
		return perfilRol;
	}
	public void setPerfilRol(PerfilRol perfilRol) {
		this.perfilRol = perfilRol;
	}
	
	
	
}
