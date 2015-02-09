package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.enumeration.cens.Activity;
import com.aehtiopicus.cens.enumeration.cens.ActivitySubType;

@Entity
@Table(name="cens_activity_feed")
public class ActivityFeed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1809224692264618901L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ultima_notificacion")
	private Date lastTimeNotified;

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_actividad")
	private ActivityType activityType;
	
	@Enumerated(EnumType.STRING)
	@Column(name="sub_tipo_actividad")
	private ActivitySubType activitySubType;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	
	
}
