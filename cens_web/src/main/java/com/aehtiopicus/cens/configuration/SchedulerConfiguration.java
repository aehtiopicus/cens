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

import com.aehtiopicus.cens.scheduler.EmailCensSchedulerJob;
import com.aehtiopicus.cens.scheduler.EmailNoLeidoCensSchedulerJob;
import com.aehtiopicus.cens.scheduler.SocialFacebookOauthCensSchedulerJob;
import com.aehtiopicus.cens.service.cens.CensServiceConstant;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;
import com.aehtiopicus.cens.social.service.SocialFacebookOauthService;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {


	private static final String UN_READ_JOB = "#{schedulerProperties['un_read_job']}";
	private static final String GENERAL_NOTIFICATION_JOB = "#{schedulerProperties['general_notification_job']}";
	private static final String SCHEDULER_ON = "#{schedulerProperties['activated']}";
	private static final String SCHEDULER_TOKEN_FB = "#{schedulerProperties['token_fb']}";
			

	
	@Value(UN_READ_JOB)
	private String unRead;
	
	@Value(GENERAL_NOTIFICATION_JOB)
	private String generalNotification;
	
	@Value(SCHEDULER_ON)
	private Boolean enabled;
	
	@Value(SCHEDULER_TOKEN_FB)
	private String fbTokenRefreshExpression;
	
	@Autowired
	private ApplicationContext applicationContext;
			
	
	@Bean(name="token_fb")
	public CronTriggerFactoryBean getFBTokenRefreshed(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(fbTokenRefreshExpression);
		ct.setDescription("fb token");
		ct.setJobDetail(getFacebookRefreshTokenJob().getObject());
		return ct;
		
	}
	
	
	
	@Bean(name="un_read_job")
	public CronTriggerFactoryBean getUnReadNotification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(unRead);
		ct.setDescription("un read");
		ct.setJobDetail(getUnReadNotificationJob().getObject());
		return ct;
		
	}
	

	
	@Bean(name="general_notification_job")
	public CronTriggerFactoryBean getNightModification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(generalNotification);
		ct.setDescription("evening");
		ct.setJobDetail(getEmailNotificationJob().getObject());
		return ct;
		
	}
	
	@Bean
	public JobDetailFactoryBean getFacebookRefreshTokenJob(){
		JobDetailFactoryBean jdfb = new JobDetailFactoryBean();
		jdfb.setJobClass(SocialFacebookOauthCensSchedulerJob.class);		
		JobDataMap jdm = new JobDataMap();
		jdm.put(CensServiceConstant.SOCIAL_FACEBOOK_OAUTH_SERVICE, applicationContext.getBean(SocialFacebookOauthService.class));		
		jdfb.setJobDataMap(jdm);
		return jdfb;
	}
	
	@Bean
	public JobDetailFactoryBean getUnReadNotificationJob(){
		JobDetailFactoryBean jdfb = new JobDetailFactoryBean();
		jdfb.setJobClass(EmailNoLeidoCensSchedulerJob.class);		
		JobDataMap jdm = new JobDataMap();
		jdm.put(CensServiceConstant.NOTIFICACION_CENS_SERVICE, applicationContext.getBean(NotificacionCensService.class));
		jdm.put(CensServiceConstant.USUARIO_CENS_SERVICE, applicationContext.getBean(UsuarioCensService.class));
		jdfb.setJobDataMap(jdm);
		return jdfb;
	}
	
	@Bean
	public JobDetailFactoryBean getEmailNotificationJob(){
		JobDetailFactoryBean jdfb = new JobDetailFactoryBean();
		jdfb.setJobClass(EmailCensSchedulerJob.class);		
		JobDataMap jdm = new JobDataMap();
		jdm.put(CensServiceConstant.NOTIFICACION_CENS_SERVICE, applicationContext.getBean(NotificacionCensService.class));
		jdm.put(CensServiceConstant.USUARIO_CENS_SERVICE, applicationContext.getBean(UsuarioCensService.class));
		jdfb.setJobDataMap(jdm);
		return jdfb;
	}
	
}

