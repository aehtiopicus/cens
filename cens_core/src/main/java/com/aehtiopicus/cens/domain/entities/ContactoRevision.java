package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.aehtiopicus.cens.enumeration.ContactoType;

@Entity
public class ContactoRevision implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9107539054281519999L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private boolean profesorContactado;
	
	@Enumerated(EnumType.STRING)
	private ContactoType tipoContacto;
	
	/**
	 * Generado automaticamente cuando es email o facebook
	 */
	@Column(length=1000)
	private String contenidoContacto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isProfesorContactado() {
		return profesorContactado;
	}

	public void setProfesorContactado(boolean profesorContactado) {
		this.profesorContactado = profesorContactado;
	}

	public ContactoType getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(ContactoType tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public String getContenidoContacto() {
		return contenidoContacto;
	}

	public void setContenidoContacto(String contenidoContacto) {
		this.contenidoContacto = contenidoContacto;
	}
	
	
	
	
}
