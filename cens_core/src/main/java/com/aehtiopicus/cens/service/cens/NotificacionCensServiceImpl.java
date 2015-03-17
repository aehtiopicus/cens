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
		

	
	@Override
	public void sendEmailNotification(Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser,
			String email,String nombreMiembroCens) throws CensException{
		
		Map<String,Object> data = generateNotifacionComentario(notificationForUser);
		data.put("nombre", nombreMiembroCens);
		
		emailCensService.enviarNotificacionEmail(data, email);
		
	}


	@Override
	public void markNotificationAsNotificated(String username) throws CensException {
			comentarioCensFeedService.markAllFeedsForUserAsNotified(username);
		
	}


	@Override
	public Map<NotificacionType,List<? extends AbstractNotificacionFeed>> getNotificationNoLeidasForUser()throws CensException {
	Map<NotificacionType,List<? extends AbstractNotificacionFeed>> allUnreadFeeds = new HashMap<NotificacionType, List<? extends AbstractNotificacionFeed>>();
		
		List<NotificacionComentarioFeed> ccfs = comentarioCensFeedService.getUnReadFeeds();
		
		if(CollectionUtils.isNotEmpty(ccfs)){
			
			allUnreadFeeds.put(NotificacionType.COMENTARIO, ccfs);
		}
		
		return allUnreadFeeds;
		
	}


	@Override
	public void sendEmailNoReadNotification(
			Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser,
			Map<String, String> asesoresMap) throws CensException {
		
		Map<String,Object> data = generateNotifacionComentario(notificationForUser);
		for(Map.Entry<String, String> asesorData : asesoresMap.entrySet()){
			data.put("nombre", asesorData.getValue());		
			emailCensService.enviarNotificacionEmailNoLeido(data, asesorData.getKey());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> generateNotifacionComentario(Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser) throws CensException{
		
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
		return data;
	}
}
