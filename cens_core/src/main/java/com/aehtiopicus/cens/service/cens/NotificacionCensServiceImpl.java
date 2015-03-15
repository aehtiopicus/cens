package com.aehtiopicus.cens.service.cens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.aspect.cens.mappers.NotificacionCensMapper;
import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.utils.CensException;

@Service
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
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
			String email,String nombreMiembroCens) throws CensException{
		Map<String,Object> data = new HashMap<>();
		if(notificationForUser.containsKey(NotificacionType.COMENTARIO)){			
			Map<ComentarioType,List<NotificacionComentarioFeed>>  sortedComentarios = notificacionCensMapper.comentarioMapper((List<NotificacionComentarioFeed>) notificationForUser.get(NotificacionType.COMENTARIO));
			Map<ComentarioTypeComentarioIdKey,Map<String,String>> informationToRetrieve = notificacionCensMapper.mapNotificationSorted(sortedComentarios);

			if(!informationToRetrieve.isEmpty()) {
				
				Set<Entry<ComentarioTypeComentarioIdKey,Map<String,String>>> entrySet = informationToRetrieve.entrySet();
				for(Entry<ComentarioTypeComentarioIdKey,Map<String,String>> value : entrySet){								
					informationToRetrieve.put(value.getKey(), comentarioCensFeedService.getCommentSource(value.getKey()));				
					
				}
			}			
			notificacionCensMapper.convertToNotificacion(sortedComentarios,informationToRetrieve);
			
			data.put("comentario", notificacionCensMapper.convertToNotificacionData(sortedComentarios));
		}
		data.put("nombre", nombreMiembroCens);
		
		emailCensService.enviarNotificacionEmail(data, email);
		
	}


	@Override
	public void markNotificationAsNotificated(String username) throws CensException {
			comentarioCensFeedService.markAllFeedsForUserAsNotified(username);
		
	}
}
