package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.utils.CensException;

public interface ProfesorCensService {

	public Profesor saveProfesor(MiembroCens miembroCens) throws CensException;

	public Profesor getProfesor(MiembroCens usuario);

	public void deleteProfesor(MiembroCens miembroCens);

}
