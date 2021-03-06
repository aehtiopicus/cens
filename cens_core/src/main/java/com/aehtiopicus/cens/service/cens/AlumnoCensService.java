package com.aehtiopicus.cens.service.cens;

import java.util.List;
import java.util.Map;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.utils.CensException;

public interface AlumnoCensService {

	public Alumno saveAlumno(MiembroCens miembroCens) throws CensException;

	public Alumno getAlumno(MiembroCens usuario);

	public void deleteAlumno(MiembroCens miembroCens);

	public Alumno findById(Long id);

	public List<Alumno> listAlumnos(RestRequest restRequest);

	public Long getTotalAlumnoFilterByProfile(RestRequest restRequest);

	public Map<Asignatura,Programa> listarAsignaturaAlumnoInscripto(Long alumnoId);

}
