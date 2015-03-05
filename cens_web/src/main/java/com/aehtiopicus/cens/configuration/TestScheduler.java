package com.aehtiopicus.cens.configuration;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.service.cens.MiembroCensService;
import com.aehtiopicus.cens.util.Utils;

public class TestScheduler extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
			System.out.println(context.getTrigger().getDescription());
			RestRequest rq = new RestRequest();
			rq.setPage(1);
			rq.setRow(5000);
			rq.setSord("asc");
			for(MiembroCens mc: ((MiembroCensService)context.getJobDetail().getJobDataMap().get("miembroCensService")).listMiembrosCens(rq)){
				System.out.println(Utils.getSon().toJson(mc));
			}
		
	
		
	}

	
}
