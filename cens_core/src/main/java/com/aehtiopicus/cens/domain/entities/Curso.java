package com.aehtiopicus.cens.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Agrupacion logica de alumnos. Los cursos en un semi presencial no son exactamente como en otros lados
 * Los alumnos puede estar o no en varios curos al mismo tiempo. 
 * @author aehtiopicus
 *
 */
@Entity
@Table(name = "CENS_CURSO")
public class Curso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8087762633456227832L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nombre;

	private int yearCurso;	

	@OneToMany(mappedBy = "curso")
	private List<Asignatura> asignaturas;

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

	public int getYearCurso() {
		return yearCurso;
	}

	public void setYearCurso(int yearCurso) {
		this.yearCurso = yearCurso;
	}


	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

}
