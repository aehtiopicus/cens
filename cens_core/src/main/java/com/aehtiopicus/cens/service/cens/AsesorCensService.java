package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.utils.CensException;

public interface AsesorCensService {

	public Asesor saveAsesor(MiembroCens miembroCens) throws CensException;

	public Asesor getAsesor(MiembroCens usuario);

	public void deleteAsesor(MiembroCens miembroCens);

}
