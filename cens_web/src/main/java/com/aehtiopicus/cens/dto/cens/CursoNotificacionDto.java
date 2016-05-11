package com.aehtiopicus.cens.dto.cens;

import java.util.HashSet;
import java.util.Set;

public class CursoNotificacionDto {

	private String nombre;
	private Long id;
	private Set<AsignaturaNotificacionDto> asignatura =  null;
	private boolean isTiempoEdicion = false;
	
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
	public Set<AsignaturaNotificacionDto> getAsignatura() {
		if(asignatura == null){
			asignatura = new HashSet<>();
		}
		return asignatura;
	}
	public void setAsignatura(Set<AsignaturaNotificacionDto> asignatura) {		
		this.asignatura = asignatura;
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
		CursoNotificacionDto other = (CursoNotificacionDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public int getCantidadNotificaciones() {
		int cantidad = 0;
		for(AsignaturaNotificacionDto anDto: asignatura){
			cantidad = cantidad +anDto.getCantidadNotificaciones();
		}
		
		return cantidad;
		
	}
	public boolean isTiempoEdicion() {
		return isTiempoEdicion;
	}
	public void setTiempoEdicion(boolean isTiempoEdicion) {
		this.isTiempoEdicion = isTiempoEdicion;
		for(AsignaturaNotificacionDto anDto : asignatura){
			anDto.setTiempoEdicion(true);			
		}
	}
	
	
}
