package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table( name = "CENS_PRECEPTOR")
public class Preceptor  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(optional = false)
	private MiembroCens miembroCens;
	
	private Boolean baja;

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

	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}	
	
	
	
	
}
