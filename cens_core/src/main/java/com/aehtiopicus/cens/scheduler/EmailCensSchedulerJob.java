package com.aehtiopicus.cens.scheduler;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;
import com.aehtiopicus.cens.utils.CensException;

public class EmailCensSchedulerJob  extends QuartzJobBean{

	public static final String NOTIFICACION_CENS_SERVICE = "notificacionCensService";

	public static final String USUARIO_CENS_SERVICE = "	usuarioCensService";
	
	private NotificacionCensService notificacionService;
	
	private UsuarioCensService usuarioCensService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		notificacionService = (NotificacionCensService) context.getJobDetail().getJobDataMap().get(NOTIFICACION_CENS_SERVICE);
		usuarioCensService = (UsuarioCensService) context.getJobDetail().getJobDataMap().get(USUARIO_CENS_SERVICE);
		try {
			List<String> usernameList = usuarioCensService.getUsuarioActivoByUserName();
			if(CollectionUtils.isNotEmpty(usernameList)){
				for(String username : usernameList){
					System.out.print(notificacionService.getNotificationForUser(username));
				}
			}
													
		} catch (CensException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
