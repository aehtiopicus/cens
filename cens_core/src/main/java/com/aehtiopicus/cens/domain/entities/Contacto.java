package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aehtiopicus.cens.enumeration.ContactoType;

@Entity
@Table(name = "CENS_CONTACTO")
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
	private MiembroCens miembroCens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContactoType getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(ContactoType tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public MiembroCens getMiembroCens() {
		return miembroCens;
	}

	public void setMiembroCens(MiembroCens miembroCens) {
		this.miembroCens = miembroCens;
	}

	
}
