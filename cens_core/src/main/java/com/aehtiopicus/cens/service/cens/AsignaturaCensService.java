package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.utils.CensException;

public interface AsignaturaCensService {

	public List<Asignatura> listAsignaturas(RestRequest restRequest);

	public List<Asignatura> saveAsignaturas(List<Asignatura> asignaturaList)
			throws CensException;

	public Long getTotalAsignaturasFilterByProfile(RestRequest restRequest);

	public void deleteAsignatura(Long asignaturaID) throws CensException;

	public Asignatura getAsignatura(Long asignaturaId);

}
