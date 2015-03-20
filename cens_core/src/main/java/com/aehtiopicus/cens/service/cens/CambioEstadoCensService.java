package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.utils.CensException;

public interface CambioEstadoCensService {

	public CambioEstadoCensFeed save(CambioEstadoCensFeed aux) throws CensException;

}
