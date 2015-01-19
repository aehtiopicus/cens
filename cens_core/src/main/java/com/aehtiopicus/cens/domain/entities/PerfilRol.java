package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;


public class PerfilRol implements  Serializable{
	
	
	private Long perfilId;
	private PerfilTrabajadorCensType perfilType;
	
	public Long getPerfilId() {
		return perfilId;
	}
	public void setPerfilId(Long perfilId) {
		this.perfilId = perfilId;
	}
	public PerfilTrabajadorCensType getPerfilType() {
		return perfilType;
	}
	public void setPerfilType(String type) {
		this.perfilType = PerfilTrabajadorCensType.getPrefilByNombre(type);
	}
	
	
	
}
