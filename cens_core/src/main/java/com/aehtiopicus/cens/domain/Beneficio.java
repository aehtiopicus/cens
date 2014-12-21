package com.aehtiopicus.cens.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="beneficio")
public class Beneficio implements Serializable, Comparable<Beneficio>{

	private static final long serialVersionUID = 8231620358032534001L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	protected String titulo;
	protected String descripcion;
	protected Boolean remunerativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean isRemunerativo() {
		if(remunerativo == null) {
			remunerativo = true;
		}
		return remunerativo;
	}

	public void setRemunerativo(Boolean remunerativo) {
		this.remunerativo = remunerativo;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj != null && obj instanceof Beneficio){
			return this.id != null && this.id.equals(((Beneficio) obj).getId());
		}
		return false;
	}

	@Override
	public int compareTo(Beneficio o) {
		return this.getTitulo().compareTo(o.getTitulo());
	}
	
}
