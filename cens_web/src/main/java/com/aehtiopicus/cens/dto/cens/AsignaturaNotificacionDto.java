package com.aehtiopicus.cens.dto.cens;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

public class AsignaturaNotificacionDto {

	private String nombre;
	private Long id;
	
	private Set<ProgramaNotificacionDto> programa =  null;
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int getCantidadNotificaciones() {
		
		int cantidad = 0;
		for(ProgramaNotificacionDto pn: programa){
			cantidad = cantidad +pn.getCantidadComnetarios();
			if(CollectionUtils.isNotEmpty(pn.getMaterial())){
				for(MaterialNotificacionDto mnDto : pn.getMaterial()){
					cantidad = cantidad +mnDto.getCantidadComnetarios();
				}
			}
		}
		return cantidad;
	}
	
	
}
