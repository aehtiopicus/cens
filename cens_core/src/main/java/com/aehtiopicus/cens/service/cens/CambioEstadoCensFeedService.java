package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionCambioEstadoFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.utils.CensException;

public interface CambioEstadoCensFeedService {

	public CambioEstadoCensFeed save(CambioEstadoCensFeed aux) throws CensException;

	public List<NotificacionCambioEstadoFeed> getGeneratedFeeds(String username)throws CensException;

	public void markAllFeedsForUserAsNotified(String username) throws CensException;

	public List<NotificacionCambioEstadoFeed> getUnReadFeeds() throws CensException;

	public void markCambioEstadoFeedAsRead(Long programaId, Long miembroId, ComentarioType programa) throws CensException;

	public List<String> getAllKeys(Long tipoId, Long miembroId,
			ComentarioType ct);

}
