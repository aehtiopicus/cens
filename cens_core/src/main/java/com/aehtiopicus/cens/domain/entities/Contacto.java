package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.aehtiopicus.cens.enumeration.ContactoType;

@Entity
public class Contacto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4408910142059061448L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private ContactoType tipoContacto;
	
	@OneToOne
	private Profesor profesor;

}
