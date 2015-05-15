package com.aehtiopicus.cens.social.service;

import com.aehtiopicus.cens.utils.CensException;

public interface SocialFacebookOauthService {

	public String makeOAuthRequest(String urlPath) throws CensException;

	public void getAccessToken(String redirectUrlPath,String code) throws CensException;

	public void refreshAccessToken() throws CensException;

}
