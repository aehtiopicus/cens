package com.aehtiopicus.cens.social.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

	protected static final String OAUTH_TYPE = "client_credentials";

	private static final String AUTHORIZATION_URL = "https://graph.facebook.com/v2.3/oauth/access_token";

	@Override
	public String makeOAuthRequest(String redirectUrlPath) throws CensException {

		try {
			logger.info("oauth facebook request");
			OAuthClientRequest request = OAuthClientRequest
					.authorizationLocation(
							"https://www.facebook.com/dialog/oauth")
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
					.tokenLocation(
							"https://graph.facebook.com/v2.3/oauth/access_token")
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId(fbApiKey).setClientSecret(fbApiSecret)
					.setRedirectURI(redirectUrlPath).setCode(code)
					.buildQueryMessage();

			RestTemplate rs = new RestTemplate();

			ClientHttpRequest chr = rs.getRequestFactory().createRequest(
					new URI(request.getLocationUri()), HttpMethod.GET);
			ClientHttpResponse chResponse = chr.execute();
			HttpMessageConverterExtractor<Map<String, Object>> converter = new HttpMessageConverterExtractor<Map<String, Object>>(
					Map.class, rs.getMessageConverters());
			Map<String, Object> dataResult = converter.extractData(chResponse);

			if(dataResult.containsKey("error")){
				throw new CensException(dataResult.get("error").toString());
			}
			
			SocialUserConnection suc = repository.findByProviderId("FACEBOOK");

			if (suc == null) {
				suc = new SocialUserConnection();
				suc.setProviderId("FACEBOOK");
			}
			suc.setAccessToken(dataResult.get("access_token").toString());
			suc.setExpireTime(Long.valueOf(dataResult.get("expires_in") != null ? dataResult
					.get("expires_in").toString() : "0"));
			suc.setSecret(fbApiSecret);
			suc.setProviderUserId(fbApiKey);

			repository.save(suc);
			refreshAccessToken();

		} catch (IOException e) {
			logger.error("Url Exception Facebook OAuth Error", e);
			throw new CensException(e);
		} catch (URISyntaxException e) {
			logger.error("Url Exception Facebook OAuth Error", e);
			throw new CensException(e);
		} catch (OAuthSystemException e) {
			logger.error("Facebook OAuth Error", e);
			throw new CensException(e);
		}

	}

	@Override
	public void refreshAccessToken() throws CensException {

		try {
			RestTemplate rs = new RestTemplate();

			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(AUTHORIZATION_URL)
					.queryParam("grant_type", OAUTH_TYPE)
					.queryParam("client_secret", fbApiSecret)
					.queryParam("client_id", fbApiKey);

//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//			
//			
//			HttpEntity<?> entity = new HttpEntity<>(headers);
//			ResponseEntity<Map> response  = rs.exchange(
//					builder.build().encode().toUri(), HttpMethod.GET, entity, Map.class);
					
			ClientHttpRequest chr = rs.getRequestFactory().createRequest(
					builder.build().encode().toUri(), HttpMethod.GET);

			ClientHttpResponse chResponse = chr.execute();
			HttpMessageConverterExtractor<Map<String, Object>> converter = new HttpMessageConverterExtractor<Map<String, Object>>(
					Map.class, rs.getMessageConverters());
			Map<String, Object> dataResult = converter.extractData(chResponse);

			if(dataResult.containsKey("error")){
				throw new CensException(dataResult.get("error").toString());
			}
			
			SocialUserConnection suc = repository.findByProviderId("FACEBOOK");
			if (suc != null) {												
					suc.setRefreshToken(dataResult.get("access_token").toString());
					repository.save(suc);

				
			} else {
				throw new CensException("Token de acceso inexistente");
			}
		} catch (IOException e) {
			logger.error("Error al tratar de refrescar el token", e);
			throw new CensException("Error al refrescar el token de accesso");
		}
	}
}
