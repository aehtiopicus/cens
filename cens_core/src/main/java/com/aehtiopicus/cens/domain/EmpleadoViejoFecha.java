package com.aehtiopicus.cens.domain;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="empleadoviejofecha")
public class EmpleadoViejoFecha implements Serializable {

	private static final long serialVersionUID = 8457714950665123662L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	protected Integer legajo;
	protected Date fechaIngresoNovatium;
	protected Date fechaEgresoNovatium;
	
	@ManyToOne
	protected MotivoBaja motivo;
	
	@ManyToOne
	@JoinColumn(name = "empleado_id", nullable = false)
	protected Empleado empleado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLegajo() {
		return legajo;
	}
	public void setLegajo(Integer legajo) {
		this.legajo = legajo;
	}
	public Date getFechaIngresoNovatium() {
		return fechaIngresoNovatium;
	}
	public void setFechaIngresoNovatium(Date fechaIngresoNovatium) {
		this.fechaIngresoNovatium = fechaIngresoNovatium;
	}
	public Date getFechaEgresoNovatium() {
		return fechaEgresoNovatium;
	}
	public void setFechaEgresoNovatium(Date fechaEgresoNovatium) {
		this.fechaEgresoNovatium = fechaEgresoNovatium;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public MotivoBaja getMotivo() {
		return motivo;
	}
	public void setMotivo(MotivoBaja motivo) {
		this.motivo = motivo;
	}

	@Transient
	public String getKey() {
		return legajo.toString() + fechaIngresoNovatium.getTime() + fechaEgresoNovatium.getTime();
	}
}
