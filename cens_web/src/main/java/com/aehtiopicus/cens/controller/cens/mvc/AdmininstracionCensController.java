package com.aehtiopicus.cens.controller.cens.mvc;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;

@Controller
public class AdmininstracionCensController extends AbstractController{

	@Secured("ROLE_ADMINISTRADOR")
	@RequestMapping(value=UrlConstant.ADMINISTRACION_MVC)
	private ModelAndView administracionMainPage(){
		return new ModelAndView(VistasConstant.ADMINISTRACION_MAIN);
	}
}
