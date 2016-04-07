package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.domain.entities.PerfilRol;

public class NotificacionDto {

	private PerfilRol perfilRol;
	
	private ActividadNotificacionDto actividad;
	
	private ComentarioNotificacionDto comentario;
	
	private Integer cantidadNotificaciones;
	

	public PerfilRol getPerfilRol() {
		return perfilRol;
	}

	public void setPerfilRol(PerfilRol perfilRol) {
		this.perfilRol = perfilRol;
	}

	public ActividadNotificacionDto getActividad() {
		return actividad;
	}

	public void setActividad(ActividadNotificacionDto actividad) {
		this.actividad = actividad;
	}

	public ComentarioNotificacionDto getComentario() {
		return comentario;
	}

	public void setComentario(ComentarioNotificacionDto comentario) {
		this.comentario = comentario;
	}

	public void setCantidadNotificaciones() {
		int cantidad = 0;
		if(actividad !=null){
			cantidad = cantidad +actividad.getCantidadNotificaciones();
		}
		
		if(comentario !=null){
			cantidad = cantidad +comentario.getCantidadNotificaciones();
		}
		cantidadNotificaciones = cantidad;
		
	}

	public Integer getCantidadNotificaciones() {
		return this.cantidadNotificaciones;
	}
			
	
	
	
	
}
