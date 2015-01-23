package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aehtiopicus.cens.enumeration.FormatoType;
import com.aehtiopicus.cens.enumeration.MaterialDidacticoUbicacionType;

@Entity
@Table(name = "CENS_MATERIAL_DIDACTICO")
public class MaterialDidactico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125152027078314823L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@Enumerated(EnumType.STRING)
	private MaterialDidacticoUbicacionType ubicacionType;
	
	@Column(length=500)
	private String ubicacion;
	
	@Enumerated(EnumType.STRING)
	private FormatoType tipoFormato;
	
	@Column(length=500)
	private String descripcionFormato;
	
	@OneToOne(optional=false)
	private Profesor profesor;
	
	@OneToOne(optional=false)
	private Programa programa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public MaterialDidacticoUbicacionType getUbicacionType() {
		return ubicacionType;
	}

	public void setUbicacionType(MaterialDidacticoUbicacionType ubicacionType) {
		this.ubicacionType = ubicacionType;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public FormatoType getTipoFormato() {
		return tipoFormato;
	}

	public void setTipoFormato(FormatoType tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	public String getDescripcionFormato() {
		return descripcionFormato;
	}

	public void setDescripcionFormato(String descripcionFormato) {
		this.descripcionFormato = descripcionFormato;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	
	
	
	
}
