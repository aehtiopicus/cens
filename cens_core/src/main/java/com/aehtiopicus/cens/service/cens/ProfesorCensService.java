package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.utils.CensException;

public interface ProfesorCensService {

	public Profesor saveProfesor(MiembroCens miembroCens) throws CensException;

	public Profesor getProfesor(MiembroCens usuario);

	public void deleteProfesor(MiembroCens miembroCens);

	public List<Profesor> listProfesores(RestRequest restRequest);

	public Long getTotalProfesoresFilterByProfile(RestRequest restRequest);

	public Profesor findById(Long id);

}
