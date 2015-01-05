package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "CENS_ASESOR")
public class Asesor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8454694815603628113L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@OneToOne(optional = true)
	private Profesor profesor;
	
	@OneToOne(optional = false)
	private MiembroCens miembroCens;

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public MiembroCens getMiembroCens() {
		return miembroCens;
	}

	public void setMiembroCens(MiembroCens miembroCens) {
		this.miembroCens = miembroCens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
