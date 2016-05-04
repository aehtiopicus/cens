package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionCambioEstadoFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.utils.CensException;

public interface CambioEstadoCensFeedService extends CensFeedService<NotificacionCambioEstadoFeed,CambioEstadoCensFeed>{
		

	public void markCambioEstadoFeedAsRead(Long programaId, Long miembroId, ComentarioType programa)
			throws CensException;

	public List<String> getAllKeys(Long tipoId, Long miembroId, ComentarioType ct);			   
	

}
