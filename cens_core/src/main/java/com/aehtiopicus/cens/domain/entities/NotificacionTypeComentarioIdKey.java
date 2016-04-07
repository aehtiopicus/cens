package com.aehtiopicus.cens.domain.entities;

import java.util.Date;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;

public class NotificacionTypeComentarioIdKey {

	private ComentarioType comentarioType;
	
	private Long tipoId;
	
	private Date fechaCreacion;
	
	private NotificacionType notificacionType;
	
	private Long toId;

	public NotificacionTypeComentarioIdKey(ComentarioType comentarioType,
			Long tipoId,Date fechaCreacion, NotificacionType notificacionType,Long toId) {
		super();
		this.comentarioType = comentarioType;
		this.tipoId = tipoId;
		this.fechaCreacion = fechaCreacion;
		this.notificacionType = notificacionType;
		this.toId = toId;
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

	public NotificacionType getNotificacionType() {
		return notificacionType;
	}
	
	
	public Long getToId() {
		return toId;
	}



	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comentarioType == null) ? 0 : comentarioType.hashCode());
		result = prime * result
				+ ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime
				* result
				+ ((notificacionType == null) ? 0 : notificacionType.hashCode());
		result = prime * result + ((tipoId == null) ? 0 : tipoId.hashCode());
		result = prime * result + ((toId == null) ? 0 : toId.hashCode());
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
		NotificacionTypeComentarioIdKey other = (NotificacionTypeComentarioIdKey) obj;
		if (comentarioType != other.comentarioType)
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (notificacionType != other.notificacionType)
			return false;
		if (tipoId == null) {
			if (other.tipoId != null)
				return false;
		} else if (!tipoId.equals(other.tipoId))
			return false;
		if (toId == null) {
			if (other.toId != null)
				return false;
		} else if (!toId.equals(other.toId))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return comentarioType.toString()+tipoId.toString()+fechaCreacion+notificacionType.toString()+toId.toString();
	}



	
	
	
}
