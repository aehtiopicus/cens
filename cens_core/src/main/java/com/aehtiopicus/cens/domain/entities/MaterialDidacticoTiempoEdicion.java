package com.aehtiopicus.cens.domain.entities;

import java.util.Date;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class MaterialDidacticoTiempoEdicion {

	private Long id;
	private Long profesorId;
	private EstadoRevisionType estadoRevision;
	private Long nroCartilla;
	private Date fechaCambioEstado;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfesorId() {
		return profesorId;
	}
	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
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
