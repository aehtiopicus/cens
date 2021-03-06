package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aehtiopicus.cens.enumeration.cens.AlumnoType;

@Entity
@Table(name = "CENS_ALUMNO")
public class Alumno implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -807436870936898668L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private long dni;
	private String nroLegajo;
	
	@Enumerated(EnumType.STRING)
	private AlumnoType alumnoType;
	
	@OneToOne(optional = false)
	private MiembroCens miembroCens;
	
	private Boolean baja = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public MiembroCens getMiembroCens() {
		return miembroCens;
	}

	public void setMiembroCens(MiembroCens miembroCens) {
		this.miembroCens = miembroCens;
	}

	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}
	
	
	
	
}

