package com.aehtiopicus.cens.service.cens;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class NotificacionCensServiceImpl implements NotificacionCensService{

	@Autowired
	private ComentarioCensFeedService comentarioCensFeedService;
	
	@Override
	public Map<NotificacionType,List<? extends AbstractNotificacionFeed>> getNotificationForUser(String username) throws CensException{
		Map<NotificacionType,List<? extends AbstractNotificacionFeed>> resultNotificationByUser = new HashMap<NotificacionType, List<? extends AbstractNotificacionFeed>>();
		
		List<NotificacionComentarioFeed> ccfs = comentarioCensFeedService.getGeneratedFeeds(username);
		
		if(CollectionUtils.isNotEmpty(ccfs)){
			Collections.sort(ccfs, new SortComentarioCollectionByDate());
			resultNotificationByUser.put(NotificacionType.COMENTARIO, ccfs);
		}
		
		return resultNotificationByUser;
	}
	
	class SortComentarioCollectionByDate implements Comparator<NotificacionComentarioFeed>{

		@Override
		public int compare(NotificacionComentarioFeed o1, NotificacionComentarioFeed o2) {
			if( o1.getFechaCreacion().before(o2.getFechaCreacion())){
				return -1;
			}
			if( o1.getFechaCreacion().after(o2.getFechaCreacion())){
				return 1;
			}else{
				return 0;
			}
		}
		
	}
}
