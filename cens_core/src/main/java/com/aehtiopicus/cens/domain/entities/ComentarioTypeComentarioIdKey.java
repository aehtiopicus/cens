package com.aehtiopicus.cens.domain.entities;

import java.util.Date;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;

public class ComentarioTypeComentarioIdKey {

	private ComentarioType comentarioType;
	
	private Long tipoId;
	
	private Date fechaCreacion;

	public ComentarioTypeComentarioIdKey(ComentarioType comentarioType,
			Long tipoId,Date fechaCreacion) {
		super();
		this.comentarioType = comentarioType;
		this.tipoId = tipoId;
		this.fechaCreacion = fechaCreacion;
	}


	
	public ComentarioType getComentarioType() {
		return comentarioType;
	}



	public Long getTipoId() {
		return tipoId;
	}

	


	public Date getFechaCreacion() {
		return fechaCreacion;
	}



	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comentarioType == null) ? 0 : comentarioType.hashCode());
		result = prime * result
				+ ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((tipoId == null) ? 0 : tipoId.hashCode());
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
		ComentarioTypeComentarioIdKey other = (ComentarioTypeComentarioIdKey) obj;
		if (comentarioType != other.comentarioType)
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (tipoId == null) {
			if (other.tipoId != null)
				return false;
		} else if (!tipoId.equals(other.tipoId))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return comentarioType.toString()+tipoId.toString()+fechaCreacion;
	}



	
	
	
}
