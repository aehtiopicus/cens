package com.aehtiopicus.cens.controller.cens;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.enums.SocialPostStateType;
import com.aehtiopicus.cens.controller.cens.validator.SocialPostValidator;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.SocialPost;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.dto.cens.SocialPostDto;
import com.aehtiopicus.cens.enumeration.cens.SocialType;
import com.aehtiopicus.cens.mapper.cens.SocialPostMapper;
import com.aehtiopicus.cens.service.cens.SocialPostCensService;
import com.aehtiopicus.cens.social.service.SocialFacebookOauthService;

@Controller
public class SocialCensRestController extends AbstractRestController{

	@Autowired
	private SocialFacebookOauthService facebookOAuth;
	
	@Autowired
	private SocialPostValidator spv;
	
	@Autowired
	private SocialPostCensService spcService;
	
	@Autowired
	private SocialPostMapper spMapper;
	
	
	@Secured("ROLE_ASESOR")
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_SOCIAL, method=RequestMethod.GET)
	public @ResponseBody Map<String,String> socialStatus(HttpServletRequest request,@RequestParam("socialType") SocialType st) throws Exception{
		
		Map<String,String> mapResult = null;
		switch(st){	
			case FACEBOOK:
				mapResult = facebookOAuth.checkFacebookTokenStatus();
			break;
		}

		
		return mapResult;
	}
	
	
	@Secured("ROLE_ASESOR")
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_SOCIAL, method=RequestMethod.DELETE)
	public @ResponseBody RestSingleResponseDto socialRevoke(HttpServletRequest request,@RequestParam("socialType") SocialType st) throws Exception{
				
		switch(st){	
			case FACEBOOK:
				 facebookOAuth.revokeAccess();
			break;
		}

		RestSingleResponseDto rsDto = new RestSingleResponseDto();
		rsDto.setMessage("Token eliminado");
		
		return rsDto;
	}
	
	@Secured("ROLE_ASESOR")
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2, method=RequestMethod.GET)
	public @ResponseBody RestSingleResponseDto OAuthConnectInit(HttpServletRequest request,@RequestParam("socialType") SocialType st,
			@RequestParam(value="key",required=false)String key,
			@RequestParam(value="secret",required=false)String secret) throws Exception{
		
		StringBuilder sb = new StringBuilder(new java.net.URL(request.getScheme(),request.getServerName(),request.getServerPort(),"").toString());
		sb.append(request.getContextPath());	
		sb.append(UrlConstant.SOCIAL_CENS_REST_OAUTH_2_CALLBACK_MVC);
		
		String urlResult = null;
		switch(st){	
			case FACEBOOK:
				urlResult = facebookOAuth.makeOAuthRequest(sb.toString().replace("{provider}", "FACEBOOK"),key,secret);
			break;
		}
		RestSingleResponseDto rsrDto = new RestSingleResponseDto();
		
		rsrDto.setMessage(urlResult);
		
		return rsrDto;
	}
	
	@Secured("ROLE_ASESOR")
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2_CALLBACK,method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<RestSingleResponseDto> OAuthConnect(HttpServletRequest request,@PathVariable("provider") SocialType provider) throws Exception{
		
		
		StringBuilder sb = new StringBuilder(new java.net.URL(request.getScheme(),request.getServerName(),request.getServerPort(),"").toString());
		sb.append(request.getContextPath());	
		sb.append(UrlConstant.SOCIAL_CENS_REST_OAUTH_2_CALLBACK_MVC);
		
		switch(provider){	
		  case FACEBOOK:
			  facebookOAuth.getAccessToken(sb.toString().replace("{provider}", "FACEBOOK"),request.getParameter("code").toString());
			  break;
		}
		
		RestSingleResponseDto rsDto = new RestSingleResponseDto();
		rsDto.setMessage("Token de access concedido");
		ResponseEntity<RestSingleResponseDto> rsrDto = new ResponseEntity<RestSingleResponseDto>(rsDto, HttpStatus.OK);
		return rsrDto;
			
	}
	
	@Secured("ROLE_ASESOR")
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2_CALLBACK_ACCESS_TOKEN,method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<RestSingleResponseDto> OAuthRefreshConnect(@PathVariable("provider") SocialType provider) throws Exception{
	
		
		switch(provider){	
		  case FACEBOOK:
			  facebookOAuth.refreshAccessToken();
			  break;
		}
		
		RestSingleResponseDto rsDto = new RestSingleResponseDto();
		rsDto.setMessage("Token de access RENOVADO");
		ResponseEntity<RestSingleResponseDto> rsrDto = new ResponseEntity<RestSingleResponseDto>(rsDto, HttpStatus.OK);
		return rsrDto;
			
	}
	
	@Secured("ROLE_ASESOR")
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2, method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends Object> postData(HttpServletRequest request,@RequestParam("provider") SocialType st,
			@RequestParam("comentarioTypeId")Long ctId,
			@RequestBody Map<String,String> messages) throws Exception{
		ResponseEntity<SocialPostDto> re = null;
		String publishString = messages.get("value");
		Programa p = spcService.findProgramaById(ctId);
		
		SocialPost old = spcService.findByPrograma(p);
		if(old!=null){			
			facebookOAuth.deleteContent(old.getPublishEventId());
			spcService.deleteSocialPost(p);
		}
		spv.validar(p);
			
		if(StringUtils.isEmpty(publishString)){
			publishString = spMapper.convertProgramaIntoSocialPostMessage(p);
		}
		
		String publishId = null;
		switch(st){	
			case FACEBOOK:			
				 publishId = facebookOAuth.publishContent(publishString);
			break;
		}
		SocialPost sp = spcService.saveSocialPost(p, publishId,st.toString(),publishString);
				
		re = new ResponseEntity<SocialPostDto>(spMapper.mapEntityToDto(sp),HttpStatus.OK);
		return re;
	}
	
	@Secured("ROLE_ASESOR")
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2, method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<? extends Object> deleteData(HttpServletRequest request,@RequestParam("provider") SocialType st,@RequestParam("comentarioTypeId")Long ctId) throws Exception{
		ResponseEntity<RestSingleResponseDto> re = null;
		
		Programa p = spcService.findProgramaById(ctId);
		
		SocialPost old = spcService.findByPrograma(p);
		if(old!=null){			
			facebookOAuth.deleteContent(old.getPublishEventId());
			spcService.deleteSocialPost(p);
			RestSingleResponseDto rsDto = new RestSingleResponseDto();
			rsDto.setMessage("Publicaci&oacute;n eliminada");
			re = new ResponseEntity<RestSingleResponseDto>(rsDto,HttpStatus.OK);
		}else{
			RestSingleResponseDto rsDto = new RestSingleResponseDto();
			rsDto.setMessage("Publicaci&oacute;n inexistente");
			re = new ResponseEntity<RestSingleResponseDto>(rsDto,HttpStatus.BAD_REQUEST);
		}

	
		return re;
	}
	
	@Secured("ROLE_ASESOR")
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value = UrlConstant.SOCIAL_CENS_REST_OAUTH_2+"/programa/{programaId}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends Object> getPostData(HttpServletRequest request,@RequestParam("provider") SocialType st,@PathVariable("programaId")Long ctId) throws Exception{
		
		Programa p = spcService.findProgramaById(ctId);
		SocialPost sp = spcService.findByPrograma(p);
		SocialPostDto spDto = null;
		if(sp!=null){			
			spDto = spMapper.mapEntityToDto(sp);
			spDto.setSocialPostStateType(SocialPostStateType.PUBLICADO);			
		}else{
			spDto = new SocialPostDto();
			spDto.setMessage(spMapper.convertProgramaIntoSocialPostMessage(p));
			spDto.setSocialPostStateType(SocialPostStateType.NO_PUBLICADO);
		
		
		}

	
		return new ResponseEntity<SocialPostDto>(spDto,HttpStatus.OK);
	}
	
	
}
