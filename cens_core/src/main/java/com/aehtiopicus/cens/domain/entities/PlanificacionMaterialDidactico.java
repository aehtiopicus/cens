package com.aehtiopicus.cens.domain.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class PlanificacionMaterialDidactico {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Profesor profesor;
	
	@Column(length=500)
	private String descripcion;
	
	@OneToMany
	private List<MaterialDidactico> materialDidactico;
	
	private int ciclo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<MaterialDidactico> getMaterialDidactico() {
		return materialDidactico;
	}

	public void setMaterialDidactico(List<MaterialDidactico> materialDidactico) {
		this.materialDidactico = materialDidactico;
	}

	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}
	
	
	
}
