package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class NotificacionCambioEstadoFeed extends AbstractNotificacionFeed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2701415506011386838L;
	
	private EstadoRevisionType estadoRevisionType;


	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}

	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}

	
	
	
}
