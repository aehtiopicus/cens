package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public class ComentarioRequestDto {

	private ComentarioType tipoType;
	private Long tipoId;
	private Long usuarioId;
	private PerfilTrabajadorCensType usuarioTipo;
	private String mensaje;
	private Long id;
	private Long parentId;
	
	
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
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	

}
