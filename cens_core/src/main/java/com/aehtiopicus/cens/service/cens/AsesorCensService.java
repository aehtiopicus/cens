package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.utils.CensException;

public interface AsesorCensService {

	Asesor saveAsesor(MiembroCens miembroCens) throws CensException;

	Asesor getAsesor(MiembroCens usuario);

}
