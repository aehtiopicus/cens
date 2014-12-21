package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Profesor extends TrabajadorCens implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7124678087292565054L;

	@OneToMany(mappedBy = "profesor")
	private List<Asignatura> asignatura;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profesor")
	private List<Contacto> contactos;

	public List<Asignatura> getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(List<Asignatura> asignatura) {
		this.asignatura = asignatura;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	
	

}
