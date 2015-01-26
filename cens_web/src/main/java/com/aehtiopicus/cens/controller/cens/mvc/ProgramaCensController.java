package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;

@Controller
public class ProgramaCensController extends AbstractController{

	@RequestMapping(value=UrlConstant.PROGRAMA_CENS_MVC, method= RequestMethod.GET)
	public ModelAndView getProgramaCreation(@RequestParam(value="disabled",required=false) String disabled,@RequestParam("asignatura") String asignatura, @PathVariable(value="id") Long asignaturaId,HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = prepareModelAndViewABM(null,asignaturaId,asignatura);
		return mav;
	}
	
	@RequestMapping(value=UrlConstant.PROGRAMA_CENS_MVC+"/{programaId}", method= RequestMethod.GET)
	public ModelAndView getProgramaABM(@RequestParam(value="disabled",required=false) String disabled,@RequestParam("asignatura") String asignatura,@PathVariable(value="programaId") Long programaId,@PathVariable(value="id") Long asignaturaId,HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = prepareModelAndViewABM(programaId,asignaturaId,asignatura);
		mav.addObject("disabled",disabled);
		return mav;
	}
	
	private ModelAndView prepareModelAndViewABM(Long id,Long asignaturaId,String asignatura){
		ModelAndView mav = new ModelAndView(VistasConstant.ASIGNATURA_PROGRAMA_ABM_VIEW);				
		mav.addObject("id",id);		
		mav.addObject("asignaturaId", asignaturaId);
		mav.addObject("asignatura",asignatura);
		return mav;
	}
}
