package com.aehtiopicus.cens.domain.entities;

import java.util.Date;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class MaterialDidacticoTiempoEdicion {

	private Long id;
	private Long miembroId;
	private EstadoRevisionType estadoRevision;
	private Long nroCartilla;
	private Date fechaCambioEstado;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMiembroId() {
		return miembroId;
	}
	public void setMiembroId(Long miembroId) {
		this.miembroId = miembroId;
	}
	public EstadoRevisionType getEstadoRevision() {
		return estadoRevision;
	}
	public void setEstadoRevision(EstadoRevisionType estadoRevision) {
		this.estadoRevision = estadoRevision;
	}
	public Long getNroCartilla() {
		return nroCartilla;
	}
	public void setNroCartilla(Long nroCartilla) {
		this.nroCartilla = nroCartilla;
	}
	public Date getFechaCambioEstado() {
		return fechaCambioEstado;
	}
	public void setFechaCambioEstado(Date fechaCambioEstado) {
		this.fechaCambioEstado = fechaCambioEstado;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MaterialDidacticoTiempoEdicion){
			return this.id.equals(((MaterialDidacticoTiempoEdicion)obj).getId());
		}
		return false;		
	}
}
