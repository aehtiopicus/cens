package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.utils.CensException;

public interface ComentarioCensFeedService {

	public void save(ComentarioCensFeed comentarioFeed);

	public List<ComentarioCensFeed> getGeneratedFeeds(String userName)
			throws CensException;

}
