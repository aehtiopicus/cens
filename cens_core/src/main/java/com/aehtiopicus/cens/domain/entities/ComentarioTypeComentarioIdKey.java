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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comentarioType == null) ? 0 : comentarioType.hashCode());
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
		if(comentarioType == null || tipoId == null || other.comentarioType == null || other.tipoId == null){
			return false;
		}
		if (comentarioType != other.comentarioType || tipoId != other.tipoId){
			return false;
		}		
		return true;
	}
	
	
	
}
