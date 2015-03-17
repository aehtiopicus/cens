package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;

public class NotificacionComentarioFeed extends AbstractNotificacionFeed implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 9166587813140033682L;


		
	@Column(name="comentariotype")
	@Enumerated(EnumType.STRING)
	private ComentarioType comentarioType;
	
	private Long tipoId;
			

	public ComentarioType getComentarioType() {
		return comentarioType;
	}

	public void setComentarioType(ComentarioType comentarioType) {
		this.comentarioType = comentarioType;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	
	
	
	
}
