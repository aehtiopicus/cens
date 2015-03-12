package com.aehtiopicus.cens.domain.entities;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;

public class ComentarioTypeComentarioIdKey {

	private ComentarioType comentarioType;
	
	private Long tipoId;

	public ComentarioTypeComentarioIdKey(ComentarioType comentarioType,
			Long tipoId) {
		super();
		this.comentarioType = comentarioType;
		this.tipoId = tipoId;
	}


	
	public ComentarioType getComentarioType() {
		return comentarioType;
	}



	public Long getTipoId() {
		return tipoId;
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
		if(comentarioType == null || tipoId == null || other.comentarioType == null || other.tipoId == null){
			return false;
		}
		if (comentarioType != other.comentarioType || tipoId != other.tipoId){
			return false;
		}		
		return true;
	}
	
	
	
}
