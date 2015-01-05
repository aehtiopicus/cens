package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "PROFESOR")
public class Profesor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7124678087292565054L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@OneToMany(mappedBy = "profesor")
	private List<Asignatura> asignatura;
	
	@OneToOne(optional = false)
	private MiembroCens miembroCens;

	public List<Asignatura> getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(List<Asignatura> asignatura) {
		this.asignatura = asignatura;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MiembroCens getMiembroCens() {
		return miembroCens;
	}

	public void setMiembroCens(MiembroCens miembroCens) {
		this.miembroCens = miembroCens;
	}
	
	
	

}
