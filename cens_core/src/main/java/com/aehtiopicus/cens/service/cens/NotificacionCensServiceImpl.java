package com.aehtiopicus.cens.service.cens;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class NotificacionCensServiceImpl implements NotificationCensService{

	@Autowired
	private ComentarioCensFeedService comentarioCensFeedService;
	
	
	public void getNotificationForUser(String username) throws CensException{
		List<ComentarioCensFeed> ccfs = comentarioCensFeedService.getGeneratedFeeds(username);
		Collections.sort(ccfs, new SortComentarioCollectionByDate());
		if(CollectionUtils.isNotEmpty(ccfs)){
			
		}
	}
	
	class SortComentarioCollectionByDate implements Comparator<ComentarioCensFeed>{

		@Override
		public int compare(ComentarioCensFeed o1, ComentarioCensFeed o2) {
			if( o1.getActivityFeed().getDateCreated().before(o2.getActivityFeed().getDateCreated())){
				return -1;
			}
			if( o1.getActivityFeed().getDateCreated().after(o2.getActivityFeed().getDateCreated())){
				return 1;
			}else{
				return 0;
			}
		}
		
	}
}
