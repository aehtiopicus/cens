package com.aehtiopicus.cens.scheduler;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aehtiopicus.cens.service.cens.UsuarioCensService;

public class SchedulerTiempoEditicionJob extends QuartzJobBean{

	private UsuarioCensService usuarioCensService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		List<Object[]> usernameList = usuarioCensService.getUsuarioActivoByUserName();
		if(CollectionUtils.isNotEmpty(usernameList)){
			
		}
	}

}
