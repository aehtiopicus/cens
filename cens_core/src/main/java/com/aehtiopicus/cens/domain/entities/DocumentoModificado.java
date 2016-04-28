package com.aehtiopicus.cens.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DocumentoModificado {

	@Column(name="fecha_cambio_estado",nullable=false,columnDefinition="DATE DEFAULT CURRENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date fechaCambioEstado;
	
	@Column(name="notificado",nullable=false,columnDefinition="BOOLEAN DEFAULT FALSE")
	private Boolean notificado;

	public Date getFechaCambioEstado() {
		return fechaCambioEstado;
	}

	public void setFechaCambioEstado(Date fechaCambioEstado) {
		this.fechaCambioEstado = fechaCambioEstado;
	}

	public Boolean getNotificado() {
		return notificado;
	}

	public void setNotificado(Boolean notificado) {
		this.notificado = notificado;
	}
	
	
}
