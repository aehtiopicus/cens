package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="cens_comentario_feed")
public class ComentarioCensFeed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3647835003686903811L;

	@Id
	@GeneratedValue(strategy  = GenerationType.AUTO)
	private Long id;
	
	@Embedded
	private ActivityFeed activityFeed;
		
	private Long comentarioCensId;
	
	@Transient
	private Long tipoId;
		

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

	public Long getComentarioCensId() {
		return comentarioCensId;
	}

	public void setComentarioCensId(Long comentarioCensId) {
		this.comentarioCensId = comentarioCensId;
	}	

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activityFeed == null) ? 0 : activityFeed.hashCode());
		result = prime
				* result
				+ ((comentarioCensId == null) ? 0 : comentarioCensId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ComentarioCensFeed other = (ComentarioCensFeed) obj;
		if (activityFeed == null) {
			if (other.activityFeed != null)
				return false;
		} else if (!activityFeed.equals(other.activityFeed))
			return false;
		if (comentarioCensId == null) {
			if (other.comentarioCensId != null)
				return false;
		} else if (!comentarioCensId.equals(other.comentarioCensId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoId == null) {
			if (other.tipoId != null)
				return false;
		} else if (!tipoId.equals(other.tipoId))
			return false;
		return true;
	}

		
	
	
}
