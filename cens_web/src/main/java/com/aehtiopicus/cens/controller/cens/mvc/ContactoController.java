package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.entities.PerfilRol;

@Controller
public class ContactoController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(ContactoController.class);
	
	@RequestMapping(value=UrlConstant.CONTACTO_CENS_MVC, method=RequestMethod.GET)
	public ModelAndView getContactoPage(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){
		PerfilRol higher = getUserInfo();
		logger.info("Devolviendo informacion del perfil");
		ModelAndView mav = new ModelAndView(VistasConstant.CONTACTO_VIEW);
		mav.addObject("perfilGenericoId", higher.getPerfilId());
		return mav;
	}
}
