package com.aehtiopicus.cens.configuration;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.aehtiopicus.cens.scheduler.EmailCensSchedulerJob;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {

	private static final String MORNGIN_JOB = "#{schedulerProperties['morning_job']}";
	private static final String NOON_JOB = "#{schedulerProperties['noon_job']}";
	private static final String EVENING_JOB = "#{schedulerProperties['evening_job']}";
	private static final String SCHEDULER_ON = "#{schedulerProperties['activated']}";
			
	@Value(MORNGIN_JOB)
	private String morning;	
	
	@Value(NOON_JOB)
	private String noon;
	
	@Value(EVENING_JOB)
	private String evening;
	
	@Value(SCHEDULER_ON)
	private Boolean enabled;
	
	@Autowired
	private ApplicationContext applicationContext;
		
	
	@Bean
	public CronTriggerFactoryBean getMorningNotification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(morning);
		ct.setDescription("morning");
		ct.setJobDetail(getEmailNotificationJob().getObject());
		return ct;
		
	}
	
	@Bean
	public CronTriggerFactoryBean getNoonModification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(noon);
		ct.setDescription("noon");
		ct.setJobDetail(getEmailNotificationJob().getObject());
		return ct;
		
	}
	
	@Bean
	public CronTriggerFactoryBean getNightModification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(evening);
		ct.setDescription("evening");
		ct.setJobDetail(getEmailNotificationJob().getObject());
		return ct;
		
	}
	
	@Bean
	public JobDetailFactoryBean getEmailNotificationJob(){
		JobDetailFactoryBean jdfb = new JobDetailFactoryBean();
		jdfb.setJobClass(EmailCensSchedulerJob.class);		
		JobDataMap jdm = new JobDataMap();
		jdm.put(EmailCensSchedulerJob.NOTIFICACION_CENS_SERVICE, applicationContext.getBean(NotificacionCensService.class));
		jdm.put(EmailCensSchedulerJob.USUARIO_CENS_SERVICE, applicationContext.getBean(UsuarioCensService.class));
		jdfb.setJobDataMap(jdm);
		return jdfb;
	}
	
	@Bean
	public SchedulerFactoryBean getScheduler(){
		if(enabled){
			SchedulerFactoryBean sfb =new SchedulerFactoryBean();
			sfb.setTriggers(getNightModification().getObject(),getMorningNotification().getObject(),getNoonModification().getObject());
			return sfb;
		}else{
			return null;
		}
	}
}

