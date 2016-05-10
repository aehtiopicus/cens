package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.domain.entities.PerfilRol;

public class NotificacionDto {

	private PerfilRol perfilRol;
	
	private GeneralNotificacionDto actividad;
	
	private GeneralNotificacionDto comentario;
	
	private GeneralNotificacionDto tiempoEdicion;
	
	private Integer cantidadNotificaciones;
	

	public PerfilRol getPerfilRol() {
		return perfilRol;
	}

	public void setPerfilRol(PerfilRol perfilRol) {
		this.perfilRol = perfilRol;
	}

	public GeneralNotificacionDto getActividad() {
		return actividad;
	}

	public void setActividad(GeneralNotificacionDto actividad) {
		this.actividad = actividad;
	}

	public GeneralNotificacionDto getComentario() {
		return comentario;
	}

	public void setComentario(GeneralNotificacionDto comentario) {
		this.comentario = comentario;
	}

	public void calculateCantidadNotificaciones() {
		int cantidad = 0;
		if(actividad != null){
			cantidad = cantidad +actividad.getCantidadNotificaciones();
		}
		
		if(comentario != null){
			cantidad = cantidad +comentario.getCantidadNotificaciones();
		}
		
		if(tiempoEdicion != null){
			cantidad = cantidad +tiempoEdicion.getCantidadNotificaciones();
		}
		cantidadNotificaciones = cantidad;
		
	}
	
	

	public GeneralNotificacionDto getTiempoEdicion() {
		return tiempoEdicion;
	}

	public void setTiempoEdicion(GeneralNotificacionDto tiempoEdicion) {
		this.tiempoEdicion = tiempoEdicion;
	}

	public Integer getCantidadNotificaciones() {
		return this.cantidadNotificaciones;
	}
			
	
	
	
	
}
