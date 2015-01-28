package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class ComentarioRequestDto {

	private ComentarioType tipoType;
	private Long tipoId;
	private Long usuarioId;
	private PerfilTrabajadorCensType usuarioTipo;
	
	public ComentarioType getTipoType() {
		return tipoType;
	}
	public void setTipoType(ComentarioType tipoType) {
		this.tipoType = tipoType;
	}
	public Long getTipoId() {
		return tipoId;
	}
	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public PerfilTrabajadorCensType getUsuarioTipo() {
		return usuarioTipo;
	}
	public void setUsuarioTipo(PerfilTrabajadorCensType usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}
	
	
	

}
