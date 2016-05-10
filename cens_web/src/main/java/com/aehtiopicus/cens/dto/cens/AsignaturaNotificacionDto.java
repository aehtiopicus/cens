package com.aehtiopicus.cens.dto.cens;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

public class AsignaturaNotificacionDto extends AbstractNotificacionItemDto{

	private Set<ProgramaNotificacionDto> programa =  null;
	
	private boolean isTEAsignatura = false;
	
	public boolean isTEAsignatura() {
		return isTEAsignatura;
	}

	public void setTEAsignatura(boolean isTEAsignatura) {
		this.isTEAsignatura = isTEAsignatura;
	}

	public Set<ProgramaNotificacionDto> getPrograma() {
		if(programa == null){
			programa = new HashSet<>();
		}
		return programa;
	}

	public void setPrograma(Set<ProgramaNotificacionDto> programa) {
		this.programa = programa;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AsignaturaNotificacionDto other = (AsignaturaNotificacionDto) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}

	public int getCantidadNotificaciones() {
		
		int cantidad = 0;
		if(CollectionUtils.isEmpty(programa) && isTEAsignatura){
			cantidad = 1;
		}else{
			for(ProgramaNotificacionDto pn: programa){
				cantidad = cantidad +pn.getCantidadComnetarios();
				if(CollectionUtils.isNotEmpty(pn.getMaterial())){
					for(MaterialNotificacionDto mnDto : pn.getMaterial()){
						cantidad = cantidad +mnDto.getCantidadComnetarios();
					}
				}
			}
		}
		return cantidad;
	}
	
	
}
