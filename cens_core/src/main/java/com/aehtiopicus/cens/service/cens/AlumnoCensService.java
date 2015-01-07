package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.utils.CensException;

public interface AlumnoCensService {

	public Alumno saveAlumno(MiembroCens miembroCens) throws CensException;

	public Alumno getAlumno(MiembroCens usuario);

	public void deleteAlumno(MiembroCens miembroCens);

}
