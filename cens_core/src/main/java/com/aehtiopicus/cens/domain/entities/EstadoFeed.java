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

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

@Entity
@Table(name="cens_estado_feed")
public class EstadoFeed implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1941888728932239237L;

	@Id
	@GeneratedValue(strategy  = GenerationType.AUTO)
	private Long id;
	
	@Embedded
	private ActivityFeed activityFeed;
	
	@Enumerated(EnumType.STRING)
	private EstadoRevisionType estadoType;

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

	public EstadoRevisionType getEstadoType() {
		return estadoType;
	}

	public void setEstadoType(EstadoRevisionType estadoType) {
		this.estadoType = estadoType;
	}
	
	
}
