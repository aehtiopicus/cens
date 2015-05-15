package com.aehtiopicus.cens.social.service;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.SocialUserConnection;
import com.aehtiopicus.cens.repository.cens.SocialUserCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class SocialFacebookOauthServiceImpl implements
		SocialFacebookOauthService {

	private static final Logger logger = LoggerFactory
			.getLogger(SocialFacebookOauthServiceImpl.class);

	private static final String FB_API_KEY = "#{socialProperties['fb_api_key']}";
	private static final String FB_API_SECRET = "#{socialProperties['fb_api_secret']}";
	private static final String FB_API_NAME_SPACE = "#{socialProperties['fb_api_name_space']}";

	@Value(FB_API_KEY)
	private String fbApiKey;

	@Value(FB_API_SECRET)
	private String fbApiSecret;

	@Value(FB_API_NAME_SPACE)
	private String fbApiNameSpace;

	@Autowired
	private SocialUserCensRepository repository;

	@Override
	public String makeOAuthRequest(String redirectUrlPath) throws CensException {

		try {
			logger.info("oauth facebook request");
			OAuthClientRequest request = OAuthClientRequest
					.authorizationProvider(OAuthProviderType.FACEBOOK)
					.setClientId(fbApiKey).setRedirectURI(redirectUrlPath)
					.buildQueryMessage();

			return request.getLocationUri();
		} catch (OAuthSystemException e) {
			logger.error("Facebook OAuth Error", e);
			throw new CensException(e);
		}

	}

	@Override
	public void getAccessToken(String redirectUrlPath, String code)
			throws CensException {
		try {
			OAuthClientRequest request = OAuthClientRequest
					.tokenProvider(OAuthProviderType.FACEBOOK)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId(fbApiKey).setClientSecret(fbApiSecret)
					.setRedirectURI(redirectUrlPath).setCode(code)
					.buildQueryMessage();

			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(
					request, GitHubTokenResponse.class);

			SocialUserConnection suc = repository.findByProviderId("FACEBOOK");

			if (suc == null) {
				suc = new SocialUserConnection();
				suc.setProviderId("FACEBOOK");
			}
			suc.setAccessToken(oAuthResponse.getAccessToken());
			suc.setExpireTime(Long
					.valueOf(oAuthResponse.getParam("expires") != null ? oAuthResponse
							.getParam("expires") : "0"));
			suc.setSecret(fbApiSecret);
			suc.setProviderUserId(fbApiKey);

			repository.save(suc);

		} catch (OAuthSystemException e) {
			logger.error("Facebook OAuth Error", e);
			throw new CensException(e);
		} catch (OAuthProblemException e) {
			logger.error("Facebook OAuthProblemException Error", e);
			throw new CensException(e);
		}

	}

	@Override
	public void refreshAccessToken() throws CensException {

		SocialUserConnection suc = repository.findByProviderId("FACEBOOK");
		if (suc != null) {
			try {
				OAuthClientRequest request = OAuthClientRequest
						.tokenProvider(OAuthProviderType.FACEBOOK)
						.setGrantType(GrantType.REFRESH_TOKEN)
						.setClientId(fbApiKey).setClientSecret(fbApiSecret)
						.setRefreshToken(suc.getAccessToken()).buildQueryMessage();

				OAuthClient oAuthClient = new OAuthClient(
						new URLConnectionClient());
				GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(
						request, GitHubTokenResponse.class);

				suc.setRefreshToken(oAuthResponse.getRefreshToken());
				repository.save(suc);

			} catch (OAuthSystemException e) {
				logger.error("Facebook OAuth Error", e);
				throw new CensException(e);
			} catch (OAuthProblemException e) {
				logger.error("Facebook OAuthProblemException Error", e);
				throw new CensException(e);
			}
		} else {
			throw new CensException("Token de acceso inexistente");
		}

	}
}
