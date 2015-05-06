package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcionResult;
import com.aehtiopicus.cens.utils.CensException;

public interface InscripcionAlumnoCensService {

	public AsignaturaInscripcionResult inscribirAlumnos(AsignaturaInscripcion ai) throws Exception;

	public int eliminarInscripcion(Long asignaturaId, Long alumnoId) throws CensException;

}
