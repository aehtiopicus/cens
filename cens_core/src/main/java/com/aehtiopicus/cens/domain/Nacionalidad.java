package com.aehtiopicus.cens.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nacionalidad")
public class Nacionalidad implements Serializable{

	
	private static final long serialVersionUID = 4069273811423898715L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	

}
