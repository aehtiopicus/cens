package com.aehtiopicus.cens.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aehtiopicus.cens.service.cens.CensServiceConstant;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;

public class EmailNoLeidoCensSchedulerJob extends QuartzJobBean {

	private NotificacionCensService notificacionService;
	
	private UsuarioCensService usuarioCensService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		notificacionService = (NotificacionCensService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.NOTIFICACION_CENS_SERVICE);
		usuarioCensService = (UsuarioCensService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.USUARIO_CENS_SERVICE);
		
		usuarioCensService.getUsuarioAsesorActivoByUserName();
		notificacionService.getNotificationNoLeidasForUser();
		
	}

}
