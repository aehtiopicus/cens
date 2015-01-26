package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.utils.CensException;

public interface AsesorCensService {

	public Asesor saveAsesor(MiembroCens miembroCens) throws CensException;

	public Asesor getAsesor(MiembroCens usuario);

	public void deleteAsesor(MiembroCens miembroCens);

	public Asesor findById(Long asesorId);

	public List<Curso> listCursos();

}
