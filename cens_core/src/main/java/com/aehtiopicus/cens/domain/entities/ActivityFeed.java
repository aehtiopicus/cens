package com.aehtiopicus.cens.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;


@Embeddable
public class ActivityFeed {

		
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date dateCreated;
	
	@Column(name="id_creador")
	private Long fromId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="prefil_creador")
	private PerfilTrabajadorCensType fromPerfil;
	
	@Column(name="id_dirigido")
	private Long toId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="prefil_dirigido")
	private PerfilTrabajadorCensType toPerfil;
	
	@Column(name="visto")
	private Boolean readed = false;
	
	@Column(name="notificado")
	private Boolean notificado = false;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ultima_notificacion")
	private Date lastTimeNotified;
	
	@Enumerated(EnumType.STRING)	
	private ComentarioType comentarioType;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public PerfilTrabajadorCensType getFromPerfil() {
		return fromPerfil;
	}

	public void setFromPerfil(PerfilTrabajadorCensType fromPerfil) {
		this.fromPerfil = fromPerfil;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public PerfilTrabajadorCensType getToPerfil() {
		return toPerfil;
	}

	public void setToPerfil(PerfilTrabajadorCensType toPerfil) {
		this.toPerfil = toPerfil;
	}

	public Boolean getReaded() {
		return readed;
	}

	public void setReaded(Boolean readed) {
		this.readed = readed;
	}

	public Boolean getNotificado() {
		return notificado;
	}

	public void setNotificado(Boolean notificado) {
		this.notificado = notificado;
	}

	public Date getLastTimeNotified() {
		return lastTimeNotified;
	}

	public void setLastTimeNotified(Date lastTimeNotified) {
		this.lastTimeNotified = lastTimeNotified;
	}

	public ComentarioType getComentarioType() {
		return comentarioType;
	}

	public void setComentarioType(ComentarioType comentarioType) {
		this.comentarioType = comentarioType;
	}


	
}
