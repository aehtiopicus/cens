package com.aehtiopicus.cens.social.service;

import java.util.Map;

import com.aehtiopicus.cens.utils.CensException;

public interface SocialFacebookOauthService {

	public String makeOAuthRequest(String urlPath,String key,String secret) throws CensException;

	public void getAccessToken(String redirectUrlPath,String code) throws CensException;

	public void refreshAccessToken() throws CensException;

	public Map<String, String> checkFacebookTokenStatus() throws CensException;

	public void revokeAccess() throws CensException;

	public void publishContent(String message) throws CensException;

}
