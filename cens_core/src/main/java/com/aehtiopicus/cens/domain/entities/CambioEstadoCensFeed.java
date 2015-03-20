package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

@Entity
@Table(name="cens_cambio_estado_feed")
public class CambioEstadoCensFeed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1863295041023959335L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Embedded
	private ActivityFeed activityFeed;
	
	private Long tipoId;
	
	@Enumerated(EnumType.STRING)
	private EstadoRevisionType estadoRevisionType;
	
	@Enumerated(EnumType.STRING)
	private EstadoRevisionType estadoRevisionTypeViejo;
	
	@Enumerated(EnumType.STRING)	
	private ComentarioType estadoComentarioType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ActivityFeed getActivityFeed() {
		return activityFeed;
	}

	public void setActivityFeed(ActivityFeed activityFeed) {
		this.activityFeed = activityFeed;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}

	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}

	public ComentarioType getEstadoComentarioType() {
		return estadoComentarioType;
	}

	public void setEstadoComentarioType(ComentarioType estadoComentarioType) {
		this.estadoComentarioType = estadoComentarioType;
	}

	public EstadoRevisionType getEstadoRevisionTypeViejo() {
		return estadoRevisionTypeViejo;
	}

	public void setEstadoRevisionTypeViejo(
			EstadoRevisionType estadoRevisionTypeViejo) {
		this.estadoRevisionTypeViejo = estadoRevisionTypeViejo;
	}
	
	
	
	
}
