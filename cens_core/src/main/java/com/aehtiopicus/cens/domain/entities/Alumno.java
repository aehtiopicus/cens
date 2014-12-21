package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.aehtiopicus.cens.enumeration.AlumnoType;

@Entity
public class Alumno implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -807436870936898668L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Asignatura> asignaturas;
	
	private String nombre;
	private String apellido;
	private long dni;
	private String nroLegajo;
	
	@Enumerated(EnumType.STRING)
	private AlumnoType alumnoType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}

	public String getNroLegajo() {
		return nroLegajo;
	}

	public void setNroLegajo(String nroLegajo) {
		this.nroLegajo = nroLegajo;
	}

	public AlumnoType getAlumnoType() {
		return alumnoType;
	}

	public void setAlumnoType(AlumnoType alumnoType) {
		this.alumnoType = alumnoType;
	}
	
	
	
}

