package com.aehtiopicus.cens.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="motivobaja")
public class MotivoBaja implements Serializable{

	private static final long serialVersionUID = 7005878020864577547L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String motivo;

	private Integer articuloLct; //Articulo Ley de Contrato de Trabajo

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public Integer getArticuloLct() {
		return articuloLct;
	}
	public void setArticuloLct(Integer articuloLct) {
		this.articuloLct = articuloLct;
	}
}
