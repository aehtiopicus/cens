package com.aehtiopicus.cens.social.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

	private String userFbKey;

	private String userFbSecret;

	@Autowired
	private SocialUserCensRepository repository;

	protected static final String OAUTH_TYPE = "client_credentials";

	private static final String AUTHORIZATION_URL = "https://graph.facebook.com/v2.3/oauth/access_token";

	private static final String CHECK_STATUS_URL = "https://graph.facebook.com/v2.3/debug_token";

	private static final String REVOKE_PERMISSIONS_URL = "https://graph.facebook.com/v2.3/{USER_ID}/permissions";
	
	private static final String PUBLISH_CONTENT_URL = "https://graph.facebook.com/v2.3/{USER_ID}/feed";

	@Override
	public Map<String, String> checkFacebookTokenStatus() throws CensException {

		Map<String, String> result = new HashMap<String, String>();
		try {

			SocialUserConnection suc = repository.findByProviderId("FACEBOOK");
			if (suc != null) {
				Map<String, Object> dataResult = facebookTokenStatus(suc);
				if (dataResult.containsKey("error")) {
					result.put("status", "inexistente");
				} else {
					result.put("status", "activo");
				}
				result.put("key", suc.getProviderUserId());
				result.put("secret", suc.getSecret());

			} else {
				result.put("status", "inexistente");
				result.put("key", fbApiKey);
				result.put("secret", fbApiSecret);
			}

		} catch (Exception e) {
			logger.error("Verification error", e);
			throw new CensException(e);
		}
		return result;

	}

	private Map<String, Object> facebookTokenStatus(SocialUserConnection suc)
			throws Exception {
		RestTemplate rs = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(CHECK_STATUS_URL)
				.queryParam("input_token", suc.getAccessToken())
				.queryParam("access_token", suc.getAccessToken());

		ClientHttpRequest chr = rs.getRequestFactory().createRequest(
				builder.build().encode().toUri(), HttpMethod.GET);

		ClientHttpResponse chResponse = chr.execute();
		HttpMessageConverterExtractor<Map<String, Object>> converter = new HttpMessageConverterExtractor<Map<String, Object>>(
				Map.class, rs.getMessageConverters());

		return converter.extractData(chResponse);
	}

	@Override
	public String makeOAuthRequest(String redirectUrlPath, String key,
			String secret) throws CensException {
		if (StringUtils.isNotEmpty(key)) {
			userFbKey = key;
		} else {

			userFbKey = fbApiKey;
		}

		if (StringUtils.isNotEmpty(secret)) {
			userFbSecret = secret;
		} else {

			userFbSecret = fbApiSecret;
		}

		try {
			logger.info("oauth facebook request");
			OAuthClientRequest request = OAuthClientRequest
					.authorizationLocation(
							"https://www.facebook.com/v2.3/dialog/oauth")
					.setClientId(userFbKey).setRedirectURI(redirectUrlPath)
					.setScope("publish_actions,manage_notifications")
					.buildQueryMessage();

			return request.getLocationUri();
		} catch (OAuthSystemException e) {
			logger.error("Facebook OAuth Error", e);
			throw new CensException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void revokeAccess() throws CensException {

		SocialUserConnection suc = repository.findByProviderId("FACEBOOK");

		if (suc != null) {
			try {
				RestTemplate rs = new RestTemplate();
				Map<String, Object> data = facebookTokenStatus(suc);
				if (data.containsKey("error")) {
					throw new CensException("No se puede invalidar el token");
				}
				

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(REVOKE_PERMISSIONS_URL.replace(
								"{USER_ID}",
								((Map<String, Object>) data.get("data")).get(
										"user_id").toString()))						
						.queryParam("access_token", suc.getAccessToken());
				
				ClientHttpRequest chr = rs.getRequestFactory().createRequest(builder.build().toUri(),
						HttpMethod.DELETE);

				ClientHttpResponse chResponse = chr.execute();
				HttpMessageConverterExtractor<Map<String, Object>> converter = new HttpMessageConverterExtractor<Map<String, Object>>(
						Map.class, rs.getMessageConverters());
			
				Map<String, Object> dataResult = converter
						.extractData(chResponse);
				if(dataResult.containsKey("success")){
					repository.delete(suc.getId());
				}else{
					throw new CensException("No se puede eliminar el token");
				}
			} catch (Exception e) {
				logger.error("Error al remover token", e);
				throw new CensException(e);
			}
		} else {
			throw new CensException("No existe token de facebook");
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
					.setClientId(userFbKey).setClientSecret(userFbSecret)
					.setRedirectURI(redirectUrlPath).setCode(code)
					.buildQueryMessage();

			RestTemplate rs = new RestTemplate();

			ClientHttpRequest chr = rs.getRequestFactory().createRequest(
					new URI(request.getLocationUri()), HttpMethod.GET);
			ClientHttpResponse chResponse = chr.execute();
			HttpMessageConverterExtractor<Map<String, Object>> converter = new HttpMessageConverterExtractor<Map<String, Object>>(
					Map.class, rs.getMessageConverters());
			Map<String, Object> dataResult = converter.extractData(chResponse);

			if (dataResult.containsKey("error")) {
				throw new CensException(dataResult.get("error").toString());
			}

			SocialUserConnection suc = repository.findByProviderId("FACEBOOK");

			if (suc == null) {
				suc = new SocialUserConnection();
				suc.setProviderId("FACEBOOK");
			}
			suc.setAccessToken(dataResult.get("access_token").toString());

			String expiresValue = dataResult.get("expires_in") != null ? dataResult
					.get("expires_in").toString() : "0";
			if (expiresValue.contains(".")) {
				expiresValue = expiresValue.substring(0,
						expiresValue.indexOf("."));
			}
			suc.setExpireTime(Long.valueOf(expiresValue));
			suc.setSecret(userFbSecret);
			suc.setProviderUserId(userFbKey);
			suc.setCurrentTime(new Date().getTime());

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

			// HttpHeaders headers = new HttpHeaders();
			// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			//
			//
			// HttpEntity<?> entity = new HttpEntity<>(headers);
			// ResponseEntity<Map> response = rs.exchange(
			// builder.build().encode().toUri(), HttpMethod.GET, entity,
			// Map.class);

			ClientHttpRequest chr = rs.getRequestFactory().createRequest(
					builder.build().encode().toUri(), HttpMethod.GET);

			ClientHttpResponse chResponse = chr.execute();
			HttpMessageConverterExtractor<Map<String, Object>> converter = new HttpMessageConverterExtractor<Map<String, Object>>(
					Map.class, rs.getMessageConverters());
			Map<String, Object> dataResult = converter.extractData(chResponse);

			if (dataResult.containsKey("error")) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void publishContent(String message) throws CensException{
		SocialUserConnection suc = repository.findByProviderId("FACEBOOK");
		try{
			Map<String,Object> data = facebookTokenStatus(suc);
			if(!data.containsKey("error")){
				
				RestTemplate rs = new RestTemplate();									

				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(PUBLISH_CONTENT_URL.replace(
								"{USER_ID}",
								((Map<String, Object>) data.get("data")).get(
										"user_id").toString())).						
						queryParam("access_token", suc.getAccessToken()).
						queryParam("message", message);
				
				ClientHttpRequest chr = rs.getRequestFactory().createRequest(builder.build().toUri(),
						HttpMethod.POST);

				ClientHttpResponse chResponse = chr.execute();
				HttpMessageConverterExtractor<Map<String, Object>> converter = new HttpMessageConverterExtractor<Map<String, Object>>(
						Map.class, rs.getMessageConverters());
			
				Map<String, Object> dataResult = converter
						.extractData(chResponse);
				if(dataResult.containsKey("error")){
					throw new CensException("No se puede publicar");
				}
				
			}else{
				throw new CensException("Token inv&aacute;lido");
			}
		}catch(Exception e){
			logger.error("Error al intentar publicar",e);
			throw new CensException(e);
		}
		
		
		
	}
}
