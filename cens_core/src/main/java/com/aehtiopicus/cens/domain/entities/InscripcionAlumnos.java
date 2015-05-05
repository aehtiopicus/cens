package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
		name = "CENS_INSCRIPCION_ALUMNO", 
		uniqueConstraints=
			@UniqueConstraint(columnNames={"cens_alumno_id", "cens_asignatura_id"}))
public class InscripcionAlumnos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 182409833865358835L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="cens_alumno_id")
	private Alumno alumno;
	
	@OneToOne
	@JoinColumn(name="cens_asignatura_id")
	private Asignatura asignatura;
	
	private Date fecha;
	
	private boolean baja;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}
	
	
}
