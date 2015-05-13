package com.aehtiopicus.cens.controller.cens;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.enumeration.cens.SocialType;
import com.aehtiopicus.cens.social.service.SocialFacebookOauthService;

@Controller
public class SocialCensRestController extends AbstractRestController{

	@Autowired
	private SocialFacebookOauthService facebookOAuth;
	
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2, method=RequestMethod.GET)
	public void OAuthConnectInit(HttpServletRequest request,@RequestParam("socialType") SocialType st) throws Exception{
		StringBuilder sb = new StringBuilder(new java.net.URL(request.getScheme(),request.getServerName(),request.getServerPort(),"").toString());
		sb.append(request.getContextPath());	
		sb.append(UrlConstant.SOCIAL_CENS_REST_OAUTH_2_CALLBACK);
		
		String urlResult = null;
		switch(st){
			case FACEBOOK:
				urlResult = facebookOAuth.makeOAuthRequest(sb.toString().replace("{provider}", "facebook"));
			break;
		}
		
		RestTemplate rs = new RestTemplate();
		ClientHttpRequest chr = rs.getRequestFactory().createRequest(new URI(urlResult),HttpMethod.GET);
		chr.execute();
	}
	
	
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2_CALLBACK,method=RequestMethod.GET)
	public void OAuthConnect(HttpServletRequest request,@PathVariable("provider") String provider) throws Exception{
		
		OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
		String code = oar.getCode();
		
	}
	
}
