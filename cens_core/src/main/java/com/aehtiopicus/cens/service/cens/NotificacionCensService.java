package com.aehtiopicus.cens.service.cens;

import java.util.List;
import java.util.Map;

import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Notificacion;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.utils.CensException;

public interface NotificacionCensService {

	public Map<NotificacionType, List<? extends AbstractNotificacionFeed>> getNotificationForUser(
			String username) throws CensException;

	public void sendEmailNotification(
			Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser,
			String email,String nombreMiembroCens)throws CensException;

	public void markNotificationAsNotificated(String string) throws CensException;

	public Map<NotificacionType,List<? extends AbstractNotificacionFeed>> getNotificationNoLeidasForUser()throws CensException ;

	public void sendEmailNoReadNotification(
			Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser,
			Map<String, String> asesoresMap) throws CensException;

	public Notificacion getNotificacionesForUser(
			Long miembroId) throws CensException;

	public Notificacion getNotificacionesUnReadForUser(Long miembroId)
			throws CensException;

	public MiembroCens getMiembroByUsername(String user);

	public int markFeedsAsIgnored(Long tipoId, ComentarioType tipoType, NotificacionType notificacionType)throws CensException;

}
