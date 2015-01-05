package com.aehtiopicus.cens.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="com.aehtiopicus.cens.domain.perfil")
@Table(name="perfil")
public class Perfil implements Serializable {

	private static final long serialVersionUID = -2593132666510879385L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long id;
	protected String nombre;
	protected String perfil;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
