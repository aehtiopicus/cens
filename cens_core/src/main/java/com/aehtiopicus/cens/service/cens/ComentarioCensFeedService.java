package com.aehtiopicus.cens.service.cens;

import java.util.List;
import java.util.Map;

import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.utils.CensException;

public interface ComentarioCensFeedService {

	public ComentarioCensFeed save(ComentarioCensFeed comentarioFeed);

	public List<NotificacionComentarioFeed> getGeneratedFeeds(String userName)
			throws CensException;

	public void obtenerFuenteDeComentarios(
			Map<ComentarioTypeComentarioIdKey, String> informationToRetrieve) throws CensException;

	public void markAllFeedsForUserAsNotified(String username) throws CensException;

}
