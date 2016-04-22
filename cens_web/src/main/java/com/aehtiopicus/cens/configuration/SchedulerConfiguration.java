package com.aehtiopicus.cens.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowire;
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
import com.aehtiopicus.cens.scheduler.SchedulerDefaultBean;
import com.aehtiopicus.cens.scheduler.SocialFacebookOauthCensSchedulerJob;
import com.aehtiopicus.cens.service.cens.CensServiceConstant;
import com.aehtiopicus.cens.service.cens.NotificacionCensService;
import com.aehtiopicus.cens.service.cens.SchedulerService;
import com.aehtiopicus.cens.service.cens.SchedulerServiceImpl;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;
import com.aehtiopicus.cens.social.service.SocialFacebookOauthService;
import com.google.common.collect.ImmutableMap;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {


	private static final String UN_READ_JOB = "#{schedulerProperties['un_read_job']}";
	private static final String GENERAL_NOTIFICATION_JOB = "#{schedulerProperties['general_notification_job']}";
	private static final String SCHEDULER_ON = "#{schedulerProperties['activated']}";
	private static final String SCHEDULER_TOKEN_FB = "#{schedulerProperties['token_fb']}";
	private static final String SCHEDULER_EDITION_TIME = "#{schedulerProperties['tiempo_edicion']}";
	
	private static final String SCHEDULER_UN_READ_JOB_ACTIVE = "#{schedulerProperties['un_read_job_activated']}";
	@Value(SCHEDULER_UN_READ_JOB_ACTIVE)
	private Boolean schUnreadJobActive;
	
	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB_ACTIVE = "#{schedulerProperties['general_notification_job_activated']}";
	@Value(SCHEDULER_GENERAL_NOTIFICATION_JOB_ACTIVE)
	private Boolean schGeneralNotificationActive;
	
	private static final String SCHEDULER_TOKEN_FB_ACTIVE = "#{schedulerProperties['token_fb_activated']}";
	@Value(SCHEDULER_TOKEN_FB_ACTIVE)
	private Boolean schTokenFBActive;
	
	private static final String SCHEDULER_EDITION_ACTIVE = "#{schedulerProperties['tiempo_edicion_activated']}";
	@Value(SCHEDULER_EDITION_ACTIVE)
	private Boolean schEditionActive;
	
	private static final String SCHEDULER_UN_READ_JOB_REAL_NAME = "#{schedulerProperties['un_read_job_display_name']}";
	@Value(SCHEDULER_UN_READ_JOB_REAL_NAME)
	private String schUnreadJobRealName;
	
	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB_REAL_NAME = "#{schedulerProperties['general_notification_job_name']}";
	@Value(SCHEDULER_GENERAL_NOTIFICATION_JOB_REAL_NAME)
	private String schGeneralNotificationRealName;
	
	private static final String SCHEDULER_TOKEN_FB_ACTIVE_REAL_NAME = "#{schedulerProperties['token_fb_display_name']}";
	@Value(SCHEDULER_TOKEN_FB_ACTIVE_REAL_NAME)
	private String schTokenFBRealName;
	
	private static final String SCHEDULER_EDITION_ACTIVE_REAL_NAME = "#{schedulerProperties['tiempo_edicion_display_name']}";
	@Value(SCHEDULER_EDITION_ACTIVE_REAL_NAME)
	private String schEditionRealName;
	
	private static final String SCHEDULER_UN_READ_JOB_DESCRIPTION = "#{schedulerProperties['un_read_job_display_description']}";
	@Value(SCHEDULER_UN_READ_JOB_DESCRIPTION)
	private String schUnreadJobDescription;
	
	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB_DESCRIPTION = "#{schedulerProperties['general_notification_job_description']}";
	@Value(SCHEDULER_GENERAL_NOTIFICATION_JOB_DESCRIPTION)
	private String schGeneralNotificationDescription;
	
	private static final String SCHEDULER_TOKEN_FB_DESCRIPTION = "#{schedulerProperties['token_fb_display_description']}";
	@Value(SCHEDULER_TOKEN_FB_DESCRIPTION)
	private String schTokenFBRealDescription;
	
	private static final String SCHEDULER_EDITION_DESCRIPTION = "#{schedulerProperties['general_notification_job_description']}";
	@Value(SCHEDULER_EDITION_DESCRIPTION)
	private String schEditionRealDescription;
			
	@PersistenceContext
	private EntityManager entityManager;
	
	@Value(UN_READ_JOB)
	private String unRead;
	
	@Value(GENERAL_NOTIFICATION_JOB)
	private String generalNotification;
	
	@Value(SCHEDULER_ON)
	private Boolean enabled;
	
	@Value(SCHEDULER_TOKEN_FB)
	private String fbTokenRefreshExpression;
	
	@Value(SCHEDULER_EDITION_TIME)
	private String editionTimeExpression;
	
	private static final String SCHEDULER_UN_READ_JOB_NAME = "un_read_job";
	private static final String SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME = "general_notification_job";
	private static final String SCHEDULER_TOKEN_FB_NAME = "token_fb";
	private static final String SCHEDULER_EDITION_TIME_NAME = "tiempo_edicion";
	private static final String SCHEDULER_DEFAULT_PROPERTIES = "defaultSchedulerProperties";
	
	@Autowired
	private ApplicationContext applicationContext;
			
	
	@Bean(name=SCHEDULER_TOKEN_FB_NAME)
	public CronTriggerFactoryBean getFBTokenRefreshed(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(fbTokenRefreshExpression);
		ct.setDescription("fb token");
		ct.setJobDetail(getFacebookRefreshTokenJob().getObject());
		return ct;
		
	}
	
	
	
	@Bean(name=SCHEDULER_UN_READ_JOB_NAME)
	public CronTriggerFactoryBean getUnReadNotification(){
		CronTriggerFactoryBean ct = new CronTriggerFactoryBean();
		ct.setCronExpression(unRead);
		ct.setDescription("un read");
		ct.setJobDetail(getUnReadNotificationJob().getObject());
		return ct;
		
	}
	

	
	@Bean(name=SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME)
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
	
	
	@Bean(name=SCHEDULER_DEFAULT_PROPERTIES)
	public Map<String,SchedulerDefaultBean> assembleDefaultProperties(){
		return new HashMap<String, SchedulerDefaultBean>(ImmutableMap.of(
				SCHEDULER_UN_READ_JOB_NAME,new SchedulerDefaultBean(SCHEDULER_UN_READ_JOB_NAME,schUnreadJobRealName,unRead,schUnreadJobActive,schUnreadJobDescription),				
				SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME,new SchedulerDefaultBean(SCHEDULER_GENERAL_NOTIFICATION_JOB_NAME, schGeneralNotificationRealName, generalNotification, schGeneralNotificationActive, schGeneralNotificationDescription),
				SCHEDULER_TOKEN_FB_NAME,new SchedulerDefaultBean(SCHEDULER_TOKEN_FB_NAME, schTokenFBRealName, fbTokenRefreshExpression, schTokenFBActive, schTokenFBRealDescription ),
				SCHEDULER_EDITION_TIME_NAME, new SchedulerDefaultBean(SCHEDULER_EDITION_TIME_NAME, schEditionRealName, editionTimeExpression, schEditionActive, schEditionRealDescription)

				));
	}
	
	@Bean(name="schedulerService",autowire=Autowire.BY_TYPE,initMethod="postConstruct")
	public SchedulerService createSchedulerService(){
		SchedulerServiceImpl impl = new SchedulerServiceImpl();
		impl.setDefaultProperties(assembleDefaultProperties());		
		return impl;
	}
	
}

