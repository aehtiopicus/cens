package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MaterialDidacticoEvaluado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6049696028105826476L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private MaterialDidactico materialDidactico;
	
	@OneToOne
	private Alumno alumno;
	
	private Double nota;
	
	@Temporal(TemporalType.DATE)
	private Date fechaEntrega;
	
	@Temporal(TemporalType.DATE)
	private Date fechaEvaluacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MaterialDidactico getMaterialDidactico() {
		return materialDidactico;
	}

	public void setMaterialDidactico(MaterialDidactico materialDidactico) {
		this.materialDidactico = materialDidactico;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}
	
	
	
}
