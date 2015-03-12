package com.aehtiopicus.cens.domain.entities;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

public abstract class AbstractNotificacionFeed {

	@Column(name="fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name="id_dirigido")
	private Long toId;
	
	@Column(name="perfil_dirigido")
	@Enumerated(EnumType.STRING)
	private PerfilTrabajadorCensType perfilDirigido;
	
	@Column(name="notificado")
	private Boolean notificado;
		
	private Long feedId;
	
	private Map<String,String> displayTextMap;
	
	private int cantidad;

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public PerfilTrabajadorCensType getPerfilDirigido() {
		return perfilDirigido;
	}

	public void setPerfilDirigido(PerfilTrabajadorCensType perfilDirigido) {
		this.perfilDirigido = perfilDirigido;
	}

	public Boolean getNotificado() {
		return notificado;
	}

	public void setNotificado(Boolean notificado) {
		this.notificado = notificado;
	}

	public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	

	public Map<String, String> getDisplayTextMap() {
		return displayTextMap;
	}

	public void setDisplayTextMap(Map<String, String> displayTextMap) {
		this.displayTextMap = displayTextMap;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
}
