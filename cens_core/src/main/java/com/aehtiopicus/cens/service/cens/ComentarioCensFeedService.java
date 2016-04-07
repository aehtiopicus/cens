package com.aehtiopicus.cens.service.cens;

import java.util.List;
import java.util.Map;

import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.utils.CensException;

public interface ComentarioCensFeedService {

	public ComentarioCensFeed save(ComentarioCensFeed comentarioFeed);

	public List<NotificacionComentarioFeed> getGeneratedFeeds(String userName,boolean email)
			throws CensException;

	public void markAllFeedsForUserAsNotified(String username) throws CensException;

	public List<Long> getAsesoresIdExcludingCaller(Long fromId);

	public void deleteAllComentarios(List<Long> list) throws CensException;

	public Map<String,String> getCommentSource(NotificacionTypeComentarioIdKey ctik)
			throws CensException;

	public void markAllFeedsFromCommentsAsRead(Long id,
			List<ComentarioCens> comentarioList) throws CensException;

	public List<NotificacionComentarioFeed> getUnReadFeeds()  throws CensException;

	public int markCommentsAsIgnored(Long tipoId, ComentarioType tipoType)throws CensException;

}
