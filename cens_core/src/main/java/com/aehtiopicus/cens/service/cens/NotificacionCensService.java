package com.aehtiopicus.cens.service.cens;

import java.util.List;
import java.util.Map;

import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.utils.CensException;

public interface NotificacionCensService {

	public Map<NotificacionType, List<? extends AbstractNotificacionFeed>> getNotificationForUser(
			String username) throws CensException;

	public void sendEmailNotification(
			Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser,
			String email,String nombreMiembroCens)throws CensException;

	public void markNotificationAsNotificated(String string) throws CensException;

	public void getNotificationNoLeidasForUser();

}
