package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "CENS_ASIGNATURA")
public class Asignatura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4996938615477212893L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	

	private String nombre;

	private String modalidad;
	
	@Column(length=1000)
	private String horarios;

	@OneToMany(mappedBy = "asignatura")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<MaterialDidactico> materialesDidacticos;
	
	@OneToMany(mappedBy = "asignaturas")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Alumno> alumnos;

	@OneToOne(optional=false)
	private Curso curso;

	@OneToOne(optional=true)	
	private Profesor profesor;
	
	@OneToOne(optional=true)
	private Profesor profesorSuplente;
	
	private boolean vigente = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public List<MaterialDidactico> getMaterialesDidacticos() {
		return materialesDidacticos;
	}

	public void setMaterialesDidacticos(List<MaterialDidactico> materialesDidacticos) {
		this.materialesDidacticos = materialesDidacticos;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public String getHorarios() {
		return horarios;
	}

	public void setHorarios(String horarios) {
		this.horarios = horarios;
	}

	public Profesor getProfesorSuplente() {
		return profesorSuplente;
	}

	public void setProfesorSuplente(Profesor profesorSuplente) {
		this.profesorSuplente = profesorSuplente;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}
	
	
	

}
