package com.aehtiopicus.cens.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aehtiopicus.cens.domain.entities.AbstractNotificacionFeed;
import com.aehtiopicus.cens.enumeration.cens.NotificacionType;
import com.aehtiopicus.cens.service.cens.CensServiceConstant;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;

public class EmailNoLeidoCensSchedulerJob extends QuartzJobBean {

	private NotificacionCensService notificacionService;
	
	private UsuarioCensService usuarioCensService;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailNoLeidoCensSchedulerJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try{
			notificacionService = (NotificacionCensService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.NOTIFICACION_CENS_SERVICE);
			usuarioCensService = (UsuarioCensService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.USUARIO_CENS_SERVICE);
		
			List<Object[]> asesores = usuarioCensService.getUsuarioAsesorActivoByUserName();
			if(CollectionUtils.isNotEmpty(asesores)){
				Map<String,String> asesoresMap = new HashMap<String, String>();
				for(Object[] username : asesores){
					asesoresMap.put(username[1].toString(),username[2].toString()+", "+username[3].toString()+", DNI: "+username[4].toString());
				}
				Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser = notificacionService.getNotificationNoLeidasForUser();
				if(!notificationForUser.isEmpty()){
					notificacionService.sendEmailNoReadNotification(notificationForUser,asesoresMap);
				}
			}
		
		}catch(Exception e){
			logger.error("Error in email scheduler",e);
		}
		
	}

}
