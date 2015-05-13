package com.aehtiopicus.cens.social.service;

import com.aehtiopicus.cens.utils.CensException;

public interface SocialFacebookOauthService {

	public String makeOAuthRequest(String urlPath) throws CensException;

}
