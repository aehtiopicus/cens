package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;

@Controller
public class ProfesorCensController extends AbstractController{

	@RequestMapping(value=UrlConstant.PROFESOR_CENS_ASIGNATURAS_MVC, method= RequestMethod.GET)
	public ModelAndView getAsignaturasProfesor(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.PROFESOR_ASIGNATURA_LIST_VIEW);				
		return mav;
	}
	
}
