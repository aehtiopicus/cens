package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cens_comentario_feed")
public class ComentarioFeed implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy  = GenerationType.AUTO)
	private Long id;
	
	@Embedded
	private ActivityFeed activityFeed;
	
	@OneToOne
	private ComentarioCens comentarioCens;

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

	public ComentarioCens getComentarioCens() {
		return comentarioCens;
	}

	public void setComentarioCens(ComentarioCens comentarioCens) {
		this.comentarioCens = comentarioCens;
	}
	
	
	
}
