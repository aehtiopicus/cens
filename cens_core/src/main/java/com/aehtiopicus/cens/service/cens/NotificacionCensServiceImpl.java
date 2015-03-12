package com.aehtiopicus.cens.service.cens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.aspect.cens.mappers.NotificacionCensMapper;
import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class NotificacionCensServiceImpl implements NotificacionCensService{

	@Autowired
	private ComentarioCensFeedService comentarioCensFeedService;
	
	@Autowired
	@Qualifier(value="emailService")
	private EmailCensService emailCensService;
	
	@Autowired
	private NotificacionCensMapper notificacionCensMapper;
	
	@Override
	public Map<NotificacionType,List<? extends AbstractNotificacionFeed>> getNotificationForUser(String username) throws CensException{
		Map<NotificacionType,List<? extends AbstractNotificacionFeed>> resultNotificationByUser = new HashMap<NotificacionType, List<? extends AbstractNotificacionFeed>>();
		
		List<NotificacionComentarioFeed> ccfs = comentarioCensFeedService.getGeneratedFeeds(username);
		
		if(CollectionUtils.isNotEmpty(ccfs)){
			
			resultNotificationByUser.put(NotificacionType.COMENTARIO, ccfs);
		}
		
		return resultNotificationByUser;
	}
		

	@SuppressWarnings("unchecked")
	@Override
	public void sendEmailNotification(Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser,
			String email) {
		if(notificationForUser.containsKey(NotificacionType.COMENTARIO)){			
			Map<ComentarioType,List<NotificacionComentarioFeed>>  sortedComentarios = notificacionCensMapper.comentarioMapper((List<NotificacionComentarioFeed>) notificationForUser.get(NotificacionType.COMENTARIO));
			Map<ComentarioTypeComentarioIdKey,String> informationToRetrieve = notificacionCensMapper.mapNotificationSorted(sortedComentarios);
			comentarioCensFeedService.obtenerFuenteDeComentarios(informationToRetrieve);//ponerle cache
		}s
		
		emailCensService.enviarNotificacionEmail(new HashMap<String,Object>(), email);
		
	}
}
