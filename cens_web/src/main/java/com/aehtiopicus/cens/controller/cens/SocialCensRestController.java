package com.aehtiopicus.cens.controller.cens;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.SocialPostValidator;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.SocialType;
import com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService;
import com.aehtiopicus.cens.service.cens.ProgramaCensService;
import com.aehtiopicus.cens.social.service.SocialFacebookOauthService;

@Controller
public class SocialCensRestController extends AbstractRestController{

	@Autowired
	private SocialFacebookOauthService facebookOAuth;
	
	@Autowired
	private ProgramaCensService programaService;
	
	@Autowired
	private MaterialDidacticoCensService materialService;
	
	@Autowired
	private SocialPostValidator spv;
	
	
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
	public @ResponseBody RestSingleResponseDto postData(HttpServletRequest request,@RequestParam("provider") SocialType st,@RequestParam("comentarioTypeId")Long ctId) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		//cochinada
		switch(ct){
		case MATERIAL:
			sb.append("Material Did&aacute;ctico ");
			MaterialDidactico m = materialService.findById(ctId);
			sb.append(m.getNombre().toUpperCase());
			sb.append(" pertenenciente al ");
			sb.append(buildAsignaturaCurso(m.getPrograma()));
			sb.append(" se encuentra disponible");
			break;
		case PROGRAMA:
			
			Programa p = programaService.findById(ctId);
			sb.append(buildAsignaturaCurso(p));
			sb.append(" se encuentra disponible");
			break;
		
		}
		
		switch(st){	
		  case FACEBOOK:
			  
			  facebookOAuth.publishContent(sb.toString());
			  break;
		}
		
		return null;
	}
	
	private String buildAsignaturaCurso(Programa p){
		StringBuilder sb = new StringBuilder();
		sb.append("Programa ");
		sb.append(p.getNombre().toUpperCase());
		sb.append(" de la asignatura ");
		sb.append(p.getAsignatura().getNombre().toUpperCase());
		sb.append(" del Curso ");
		sb.append(p.getAsignatura().getCurso().getNombre().toUpperCase());
		sb.append("(");
		sb.append(p.getAsignatura().getCurso().getYearCurso());
		sb.append(") ");
		sb.append("Dictada por, Prof: ");
		if(p.getAsignatura().getProfesorSuplente()!=null){
			sb.append(p.getAsignatura().getProfesorSuplente().getMiembroCens().getApellido().toUpperCase());
		}else{
			sb.append(p.getAsignatura().getProfesor().getMiembroCens().getApellido().toUpperCase());
		}
		return sb.toString();
	}
	
}
