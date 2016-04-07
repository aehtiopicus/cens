package com.aehtiopicus.cens.scheduler;

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

public class EmailCensSchedulerJob  extends QuartzJobBean{

	
	private NotificacionCensService notificacionService;
	
	private UsuarioCensService usuarioCensService;
	
	private static final int threadSize = 20;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailCensSchedulerJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		notificacionService = (NotificacionCensService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.NOTIFICACION_CENS_SERVICE);
		usuarioCensService = (UsuarioCensService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.USUARIO_CENS_SERVICE);
	
			List<Object[]> usernameList = usuarioCensService.getUsuarioActivoByUserName();
			if(CollectionUtils.isNotEmpty(usernameList)){
				int init = 0;
				int subList = usernameList.size()/threadSize;
				boolean modulo = usernameList.size()%threadSize > 0;
				if(subList > 0){
					for(init = 0; init<subList; init++){
						createThreads(usernameList.subList(init*threadSize, (1+init)*threadSize));
					}
					if(modulo){
						createThreads(usernameList.subList(init*threadSize, usernameList.size()));
					}					
				}else{
					createThreads(usernameList.subList(0, usernameList.size()));
				}
																	
			}
	}
	
	private void createThreads(List<Object[]> usernameSubList){
		new emailThread(usernameSubList).start();
	}
	class emailThread extends Thread{
		List<Object[]> usernameList;
		public emailThread(List<Object[]> usernameList){
			this.usernameList = usernameList;
		}
		@Override
		public void run() {
			try{
				if(CollectionUtils.isNotEmpty(usernameList)){
					for(Object[] username : usernameList){
						Map<NotificacionType, List<? extends AbstractNotificacionFeed>> notificationForUser =notificacionService.getNotificationForUser(username[0].toString(),true);
						if(!notificationForUser.isEmpty()){
							notificacionService.sendEmailNotification(notificationForUser,username[1].toString(),username[2].toString()+", "+username[3].toString()+", DNI: "+username[4].toString());
						}
						notificacionService.markNotificationAsNotificated(username[0].toString());
						
					}
				}
			}catch(Exception e){
				logger.error("Error in email scheduler",e);
			}
		}
		
		
	}

}
