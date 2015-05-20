package com.aehtiopicus.cens.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aehtiopicus.cens.service.cens.CensServiceConstant;
import com.aehtiopicus.cens.social.service.SocialFacebookOauthService;

public class SocialFacebookOauthCensSchedulerJob extends QuartzJobBean{

	private SocialFacebookOauthService fbOauthService;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(SocialFacebookOauthCensSchedulerJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try{
			fbOauthService = (SocialFacebookOauthService) context.getJobDetail().getJobDataMap().get(CensServiceConstant.SOCIAL_FACEBOOK_OAUTH_SERVICE);			
		
			fbOauthService.refreshAccessToken();			
		
		}catch(Exception e){
			logger.error("Error in facebook oauth scheduler",e);
		}
		
	}
}
