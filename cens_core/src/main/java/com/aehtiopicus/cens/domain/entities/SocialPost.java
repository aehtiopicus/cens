package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cens_social_postted_data")
public class SocialPost implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4584091456602773863L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String publishEventId;
	
	private String provider;
	
	@OneToOne
	@JoinColumn(name="programa_id")
	private Programa programa;
	
	@Column(name="post_message",length=1000)
	private String message;
	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getPublishEventId() {
		return publishEventId;
	}




	public void setPublishEventId(String publishEventId) {
		this.publishEventId = publishEventId;
	}




	public String getProvider() {
		return provider;
	}




	public void setProvider(String provider) {
		this.provider = provider;
	}




	public Programa getPrograma() {
		return programa;
	}




	public void setPrograma(Programa programa) {
		this.programa = programa;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




}
