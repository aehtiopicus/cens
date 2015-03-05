package com.aehtiopicus.cens.configuration;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.aehtiopicus.cens.service.cens.MiembroCensService;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
		
	
	@Bean
	public CronTriggerFactoryBean getMorningNotification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression("10 * *  * * ?");
		ct.setDescription("manana");
		ct.setJobDetail(getEmailNotificationJob().getObject());
		return ct;
		
	}
	
	@Bean
	public CronTriggerFactoryBean getNoonModification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression("25 * *  * * ?");
		ct.setDescription("medio dia");
		ct.setJobDetail(getEmailNotificationJob().getObject());
		return ct;
		
	}
	
	@Bean
	public CronTriggerFactoryBean getNightModification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression("35 * *  * * ?");
		ct.setDescription("noche");
		ct.setJobDetail(getEmailNotificationJob().getObject());
		return ct;
		
	}
	
	@Bean
	public JobDetailFactoryBean getEmailNotificationJob(){
		JobDetailFactoryBean jdfb = new JobDetailFactoryBean();
		jdfb.setJobClass(TestScheduler.class);
		Object obj = applicationContext.getBean(MiembroCensService.class);
		JobDataMap jdm = new JobDataMap();
		jdm.put("miembroCensService", obj);
		jdfb.setJobDataMap(jdm);
		return jdfb;
	}
	
	@Bean
	public SchedulerFactoryBean getScheduler(){
		SchedulerFactoryBean sfb =new SchedulerFactoryBean();
		sfb.setTriggers(getNightModification().getObject(),getMorningNotification().getObject(),getNoonModification().getObject());
		return sfb;
	}
}

