package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.utils.CensException;

public interface CensFeedService<K extends AbstractNotificacionFeed,M> {

	public List<K> getGeneratedFeeds(String username, boolean email) throws CensException;
	
	public List<K> getUnReadFeeds() throws CensException;
	
	public int markCommentsAsIgnored(Long tipoId, ComentarioType tipoType) throws CensException;
	
	public M save(M mValue)throws CensException;
	
	public void markAllFeedsForUserAsNotified(String username) throws CensException;
}
