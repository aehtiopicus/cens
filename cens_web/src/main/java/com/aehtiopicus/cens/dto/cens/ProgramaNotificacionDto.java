package com.aehtiopicus.cens.dto.cens;

import java.util.HashSet;
import java.util.Set;

public class ProgramaNotificacionDto extends AbstractNotificacionItemDto{

	private Set<MaterialNotificacionDto> material = null;

	public Set<MaterialNotificacionDto> getMaterial() {
		if(material == null){
			material =  new HashSet<>();
		}
		return material;
	}

	public void setMaterial(Set<MaterialNotificacionDto> material) {
		this.material = material;
	}
	
	
}
