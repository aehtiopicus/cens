package com.aehtiopicus.cens.dto.cens;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

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

	@Override
	public void setTiempoEdicion(boolean isTiempoEdicion) {		
		super.setTiempoEdicion(isTiempoEdicion);
		if(CollectionUtils.isNotEmpty(material)){
			for(MaterialNotificacionDto mnDto : material){
				mnDto.setCantidadComnetarios(1);
				mnDto.setTiempoEdicion(isTiempoEdicion);
			}
		}else{
			this.setCantidadComnetarios(1);
		}
	}
	
	
	
	
}
