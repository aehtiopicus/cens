package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.utils.CensException;

public interface AsignaturaCensService {

	public List<Asignatura> listAsignaturas(RestRequest restRequest);

	public List<Asignatura> saveAsignaturas(List<Asignatura> asignaturaList)
			throws CensException;

	public Long getTotalAsignaturasFilterByProfile(RestRequest restRequest);

	public void deleteAsignatura(Long asignaturaID) throws CensException;

	public Asignatura getAsignatura(Long asignaturaId);

	public List<Asignatura> findAsignaturasActivasByProfesor(Profesor findOneByMiembroCens);

	public void removeProfesorFromAsignaturas(Profesor findById);

	public Long countAsignaturasActivasByProfesor(Profesor profesor);

	public List<Programa> getProgramasForAsignaturas(Long id);

	public List<Curso> listarAsignaturasByProfesor(Long id);

}
