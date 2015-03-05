package com.aehtiopicus.cens.configuration;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {

	@Bean
	public CronTriggerFactoryBean getTrigger(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression("*/5 * *  * * ?");
		ct.setJobDetail(getJob().getObject());
		return ct;
		
	}
	@Bean
	public JobDetailFactoryBean getJob(){
		JobDetailFactoryBean jdfb = new JobDetailFactoryBean();
		jdfb.setJobClass(TestScheduler.class);
		return jdfb;
	}
	
	@Bean
	public SchedulerFactoryBean getScheduler(){
		SchedulerFactoryBean sfb =new SchedulerFactoryBean();
		sfb.setTriggers(getTrigger().getObject());
		return sfb;
	}
}

