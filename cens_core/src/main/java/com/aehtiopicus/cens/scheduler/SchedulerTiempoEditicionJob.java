package com.aehtiopicus.cens.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aehtiopicus.cens.service.cens.CensServiceConstant;
import com.aehtiopicus.cens.service.cens.TiempoEdicionCensService;
import com.aehtiopicus.cens.utils.CensException;

public class SchedulerTiempoEditicionJob extends QuartzJobBean{

	
	private TiempoEdicionCensService tiempoEdicionCensService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		tiempoEdicionCensService = (TiempoEdicionCensService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.TIEMPO_EDICION_CENS_SERVICE);
		try {
			tiempoEdicionCensService.guardarEntradasTiempoEdicion(tiempoEdicionCensService.ensamblarEntradasTiempoEdicion());
		} catch (CensException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
