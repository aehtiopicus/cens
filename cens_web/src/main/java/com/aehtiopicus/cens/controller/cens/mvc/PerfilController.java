package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;

@Controller
public class PerfilController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(PerfilController.class);
	
	@RequestMapping(value={UrlConstant.PERFIL_CENS_MVC}, method=RequestMethod.GET)
	public ModelAndView getPerfil(HttpServletRequest request, HttpServletResponse respone, Principal principal) throws Exception{
		
		logger.info("Devolviendo informacion del miembro");
		ModelAndView mav = new ModelAndView(VistasConstant.PERFIL_VIEW);
		mav.addObject("miembroId", getMiembroInfo());
		return mav;
	}
}
