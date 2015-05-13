package com.aehtiopicus.cens.social.service;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.utils.CensException;

@Service
public class SocialFacebookOauthServiceImpl implements SocialFacebookOauthService{

	private static final Logger logger = LoggerFactory.getLogger(SocialFacebookOauthServiceImpl.class);
	
	private static final String FB_API_KEY = "#{socialProperties['fb_api_key']}";
	private static final String FB_API_SECRET = "#{socialProperties['fb_api_secret']}";
	private static final String FB_API_NAME_SPACE = "#{socialProperties['fb_api_name_space']}";
	
	@Value(FB_API_KEY)
	private String fbApiKey;
	
	@Value(FB_API_SECRET)
	private String fbApiSecret;
	
	@Value(FB_API_NAME_SPACE)
	private String fbApiNameSpace;

	
	@Override
	public String makeOAuthRequest(String redirectUrlPath) throws CensException{
		
		try {
			logger.info("oauth facebook request");
			OAuthClientRequest request = OAuthClientRequest
					   .authorizationProvider(OAuthProviderType.FACEBOOK)
					   .setClientId(fbApiKey)
					   .setRedirectURI(redirectUrlPath)
					   .buildQueryMessage();
			
			return request.getLocationUri();
		} catch (OAuthSystemException e) {
			logger.error("Facebook OAuth Error",e);
			throw new CensException(e);
		}
		
	}
}
