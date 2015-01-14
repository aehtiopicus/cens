package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;

@Controller
public class CursoCensController extends AbstractController{

	
	@RequestMapping(value=UrlConstant.CURSO_CENS_MVC, method= RequestMethod.GET)
	public ModelAndView getCursoMainPage(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.CURSO_LIST_VIEW);			
		return mav;
	}
	
	@RequestMapping(value=UrlConstant.CURSO_CENS_ABM_MVC+"/{id}", method= RequestMethod.GET)
	public ModelAndView getCursoABMData(@PathVariable(value="id") Long id,HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.CURSO_LIST_ABM_VIEW);
		mav.addObject("id", id);
		return mav;
	}
	
	@RequestMapping(value=UrlConstant.CURSO_CENS_ABM_MVC, method= RequestMethod.GET)
	public ModelAndView getCursoABM(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.CURSO_LIST_ABM_VIEW);			
		return mav;
	}
}
