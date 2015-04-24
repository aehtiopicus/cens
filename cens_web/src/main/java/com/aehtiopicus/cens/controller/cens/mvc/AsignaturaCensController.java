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
public class AsignaturaCensController extends AbstractController{

	@RequestMapping(value=UrlConstant.ASIGNATURA_CENS_MVC, method= RequestMethod.GET)
	public ModelAndView getAsignaturaMainPage(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.ASIGNATURA_LIST_VIEW);				
		return mav;
	}
	
	@RequestMapping(value={UrlConstant.ASIGNATURA_CENS_ABM_MVC+"/{id}"}, method = RequestMethod.GET)
	public ModelAndView getABMData(@PathVariable(value="id") Long id, HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){
		return prepareModelAndViewABM(id);
	}
	
	@RequestMapping(value={UrlConstant.ASIGNATURA_CENS_ABM_MVC}, method = RequestMethod.GET)
	public ModelAndView getCreacionData(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){
		return prepareModelAndViewABM(null);
	}
	
	@RequestMapping(value={UrlConstant.ASIGNATURA_ALUMNO_CENS_ABM_MVC}, method = RequestMethod.GET)
	public ModelAndView getCreacionData(@PathVariable(value="asignaturaId")Long id,HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){
		ModelAndView mav = new ModelAndView(VistasConstant.ASIGNATURA_ALUMNO_VIEW);				
		mav.addObject("asignaturaId",id);		
		return mav;
	}
	
	private ModelAndView prepareModelAndViewABM(Long id){
		ModelAndView mav = new ModelAndView(VistasConstant.ASIGNATURA_LIST_ABM_VIEW);				
		mav.addObject("id",id);		
		return mav;
	}
	

}
