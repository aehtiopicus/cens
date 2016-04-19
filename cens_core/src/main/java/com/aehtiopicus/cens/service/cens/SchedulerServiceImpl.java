package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImpl implements SchedulerService{

	 
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	public void setSchedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean) {
		this.schedulerFactoryBean = schedulerFactoryBean;
		schedulerFactoryBean.getScheduler().get

	    Scheduler scheduler = schedulerFactoryBean.getScheduler();
	    TriggerKey triggerKey = new TriggerKey("timeSyncTrigger");
	    CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
	    trigger.setCronExpression(newCronExpression );
	    scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	
}
