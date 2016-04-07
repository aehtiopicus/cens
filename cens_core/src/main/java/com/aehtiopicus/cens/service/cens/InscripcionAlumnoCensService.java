package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcionResult;
import com.aehtiopicus.cens.domain.entities.InscripcionAlumnos;
import com.aehtiopicus.cens.utils.CensException;

public interface InscripcionAlumnoCensService {

	public AsignaturaInscripcionResult inscribirAlumnos(AsignaturaInscripcion ai) throws Exception;

	public int eliminarInscripcion(Long asignaturaId, Long alumnoId) throws CensException;

	public List<InscripcionAlumnos> listInscripcionAlumno(Long alumnoId);

	public void eliminarInscripcionAsignatura(Long asignaturaID) throws  CensException;

}
