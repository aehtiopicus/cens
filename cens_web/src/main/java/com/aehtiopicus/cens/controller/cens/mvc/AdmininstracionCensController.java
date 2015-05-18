package com.aehtiopicus.cens.controller.cens.mvc;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;

@Controller
public class AdmininstracionCensController extends AbstractController{

	@Secured("ROLE_ASESOR")
	@RequestMapping(value=UrlConstant.ADMINISTRACION_MVC)
	public ModelAndView administracionMainPage(){
		return new ModelAndView(VistasConstant.ADMINISTRACION_MAIN);
	}
	
	
	@Secured("ROLE_ASESOR")
	@RequestMapping(value=UrlConstant.SOCIAL_CENS_REST_OAUTH_2_CALLBACK_MVC)
	public ModelAndView administracionMainPageOauthCallback(@PathVariable("provider")String provider, 
			@RequestParam(value="code", required = false)String code,
			@RequestParam(value="error_code", required = false)String error_code){
		ModelAndView mav = new ModelAndView(VistasConstant.ADMINISTRACION_MAIN);
		mav.addObject("oauth", "completed");
		mav.addObject("provider", provider);
		mav.addObject("code", code);
		mav.addObject("error_code", error_code);
		return mav;
	}
}

