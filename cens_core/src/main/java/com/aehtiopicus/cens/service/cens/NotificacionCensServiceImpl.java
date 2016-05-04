package com.aehtiopicus.cens.service.cens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.aspect.cens.mappers.NotificacionCensMapper;
import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Notificacion;
import com.aehtiopicus.cens.domain.entities.NotificacionCambioEstadoFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.PerfilRol;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
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
	
	@Autowired
	private MiembroCensService miembroCensService;
	
	@Autowired
	private RolCensService roleCensService;
	
	@Autowired
	private CambioEstadoCensFeedService cambioEstadoCensFeedService;
	
	@Autowired
	private TiempoEdicionCensFeedService tiempoEdicionCensFeedService;
	
	@Override
	public Map<NotificacionType,List<? extends AbstractNotificacionFeed>> getNotificationForUser(String username,boolean email) throws CensException{
		Map<NotificacionType,List<? extends AbstractNotificacionFeed>> resultNotificationByUser = new HashMap<>();
		
		addNotification(username,email,comentarioCensFeedService,NotificacionType.COMENTARIO,resultNotificationByUser);
		addNotification(username,email,cambioEstadoCensFeedService,NotificacionType.ACTIVIDAD,resultNotificationByUser);
		addNotification(username, email, tiempoEdicionCensFeedService, NotificacionType.TIEMPO_EDICION, resultNotificationByUser);
		return resultNotificationByUser;
	}
	
	private <K extends AbstractNotificacionFeed,M> void addNotification(String username, boolean email,CensFeedService<K, M> feedService,NotificacionType nt,Map<NotificacionType,List<? extends AbstractNotificacionFeed>> resultNotificationByUser) throws CensException{
		List<K> ncefl = feedService.getGeneratedFeeds(username,email);
		if(CollectionUtils.isNotEmpty(ncefl)){
			resultNotificationByUser.put(nt, ncefl);
		}
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
			cambioEstadoCensFeedService.markAllFeedsForUserAsNotified(username);
		
	}


	@Override
	public Map<NotificacionType,List<? extends AbstractNotificacionFeed>> getNotificationNoLeidasForUser()throws CensException {
	
		Map<NotificacionType,List<? extends AbstractNotificacionFeed>> allUnreadFeeds = new HashMap<NotificacionType, List<? extends AbstractNotificacionFeed>>();
		
		List<NotificacionComentarioFeed> ccfs = comentarioCensFeedService.getUnReadFeeds();
		
		if(CollectionUtils.isNotEmpty(ccfs)){
			
			allUnreadFeeds.put(NotificacionType.COMENTARIO, ccfs);
		}
		
		List<NotificacionCambioEstadoFeed> ncefl = cambioEstadoCensFeedService.getUnReadFeeds();

		if(CollectionUtils.isNotEmpty(ncefl)){
		
			allUnreadFeeds.put(NotificacionType.ACTIVIDAD, ncefl);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String,Object> generateNotifacionComentario(Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser) throws CensException{
		
		Map<String,Object> data = new HashMap<>();
		if(notificationForUser.containsKey(NotificacionType.COMENTARIO)){			
			Map<ComentarioType,List<NotificacionComentarioFeed>>  sortedComentarios = notificacionCensMapper.comentarioMapper((List<NotificacionComentarioFeed>) notificationForUser.get(NotificacionType.COMENTARIO));
			Map<NotificacionTypeComentarioIdKey,Map<String,String>> informationToRetrieve = notificacionCensMapper.mapNotificationSorted((Map)sortedComentarios,NotificacionType.COMENTARIO);

			setInformationData(informationToRetrieve);		
			notificacionCensMapper.convertToComentarioNotificacion(sortedComentarios,informationToRetrieve);
			
			data.put(NotificacionType.COMENTARIO.name(), notificacionCensMapper.convertToNotificacionData((Map)sortedComentarios));
		}
		if(notificationForUser.containsKey(NotificacionType.ACTIVIDAD)){
			Map<ComentarioType,List<NotificacionCambioEstadoFeed>>  sortedActividad =notificacionCensMapper.actividadMapper( (List<NotificacionCambioEstadoFeed>) notificationForUser.get(NotificacionType.ACTIVIDAD));
			Map<NotificacionTypeComentarioIdKey,Map<String,String>> informationToRetrieve = notificacionCensMapper.mapNotificationSorted((Map)sortedActividad,NotificacionType.ACTIVIDAD);

			setInformationData(informationToRetrieve);			
			notificacionCensMapper.convertToActividadNotificacion(sortedActividad,informationToRetrieve);
			
			data.put(NotificacionType.ACTIVIDAD.name(), notificacionCensMapper.convertToNotificacionData((Map)sortedActividad));
			
		}
		return data;
	}

	private void setInformationData(Map<NotificacionTypeComentarioIdKey,Map<String,String>> informationToRetrieve) throws CensException{
		
		if(!informationToRetrieve.isEmpty()) {
			
			
			for(Entry<NotificacionTypeComentarioIdKey,Map<String,String>> value : informationToRetrieve.entrySet()){								
				informationToRetrieve.put(value.getKey(), comentarioCensFeedService.getCommentSource(value.getKey()));				
				
			}
		}		
	}


	@Override
	public Notificacion getNotificacionesForUser(Long miembroId) throws CensException {
		String username = miembroCensService.getMiembroCens(miembroId).getUsuario().getUsername();
		PerfilRol pr = getMayorRol(username);
		
		Map<NotificacionType,List<? extends AbstractNotificacionFeed>> resultMap = getNotificationForUser(username,false);
		Map<String,Object> data = null;
		if(!resultMap.isEmpty()){
			data = generateNotifacionComentario(resultMap);
		}
		Notificacion n = new Notificacion();
		n.setData(data);
		n.setPerfilRol(pr);
		return n;
	}
	
	private PerfilRol getMayorRol(String username){
		PerfilRol mayor = null;
		for(PerfilRol roles : roleCensService.getPerfilIdBasedOnUsuario(username)){
			if(mayor ==null){
				mayor = roles;
			}else if(roles.getPerfilType().getPrioridad()< mayor.getPerfilType().getPrioridad()){
				mayor = roles;
			}
		}
		return mayor;
	}
	@Override
	public Notificacion getNotificacionesUnReadForUser(Long miembroId) throws CensException {
		
		PerfilRol pr = getMayorRol(miembroCensService.getMiembroCens(miembroId).getUsuario().getUsername());
		
			if(pr.getPerfilType().equals(PerfilTrabajadorCensType.ASESOR)){
				Map<NotificacionType,List<? extends AbstractNotificacionFeed>> resultMap = getNotificationNoLeidasForUser();
				Map<String,Object> data = null;
				if(!resultMap.isEmpty()){
					data = generateNotifacionComentario(resultMap);
				}
				Notificacion n = new Notificacion();
				n.setData(data);
				n.setPerfilRol(pr);
				return n;
			}else{
		
				throw new CensException("El usuario no puede realizar esta petici&oacute;n");
			}
		
	}



	@Override
	public MiembroCens getMiembroByUsername(String user) {
		return miembroCensService.getMiembroCensByUsername(user);
	}



	@Override
	public int markFeedsAsIgnored(Long tipoId, ComentarioType tipoType, NotificacionType notificacionType)
			throws CensException {
		switch(notificacionType){
		
		case COMENTARIO:
			return comentarioCensFeedService.markCommentsAsIgnored(tipoId,tipoType);
		case ACTIVIDAD:
			return cambioEstadoCensFeedService.markCommentsAsIgnored(tipoId,tipoType);
		default:
			throw new CensException("No se indic&oacute; el tipo de feed");		
		
		}	
		
	}
}
