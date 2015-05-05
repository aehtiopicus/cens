package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcionResult;

public interface InscripcionAlumnoCensService {

	public AsignaturaInscripcionResult inscribirAlumnos(AsignaturaInscripcion ai) throws Exception;

}
