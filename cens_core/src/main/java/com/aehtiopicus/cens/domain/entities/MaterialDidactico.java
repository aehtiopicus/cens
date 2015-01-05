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
@Table(name = "MATERIAL_DIDACTICO")
public class MaterialDidactico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125152027078314823L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Asignatura asignatura;
	
	@Enumerated(EnumType.STRING)
	private MaterialDidacticoUbicacionType ubicacionType;
	
	@Column(length=500)
	private String ubicacion;
	
	@Enumerated(EnumType.STRING)
	private FormatoType tipoFormato;
	
	@Column(length=500)
	private String descripcionFormato;
	
	
	
}
