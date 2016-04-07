package com.aehtiopicus.cens.domain.entities;

import java.util.List;

public class AsignaturaInscripcion {

	private Asignatura asignatura;
	
	private List <Alumno> alumnos;

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	
}
