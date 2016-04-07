package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;
import com.aehtiopicus.cens.mapper.cens.RolCensMapper;
import com.aehtiopicus.cens.service.cens.RolCensService;


@Controller
public class MiembroCensController extends AbstractController{

	@Autowired
	private RolCensService rolCensService;
	@Autowired
	private RolCensMapper rolCensMapper;
	
	@RequestMapping(value=UrlConstant.MIEMBRO_CENS, method= RequestMethod.GET)
	public ModelAndView getMiembrosCensMainPage(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.MIEMBRO_LIST_VIEW);		
		mav.addObject("perfilDto",rolCensMapper.convertRolTypeToRolDTO(rolCensService.listPerfil()));
		return mav;
	}
	
	@RequestMapping(value={UrlConstant.MIEMBRO_CENS_ABM+"/{id}"}, method = RequestMethod.GET)
	public ModelAndView getABMData(@PathVariable(value="id") Long id, HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){
		return prepareModelAndViewABM(id);
	}
	
	@RequestMapping(value={UrlConstant.MIEMBRO_CENS_ABM}, method = RequestMethod.GET)
	public ModelAndView getCreacionData(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){
		return prepareModelAndViewABM(null);
	}
	
	private ModelAndView prepareModelAndViewABM(Long id){
		ModelAndView mav = new ModelAndView(VistasConstant.MIEMBRO_ABM_VIEW);		
		mav.addObject("perfilDto",rolCensMapper.convertRolTypeToRolDTO(rolCensService.listPerfil()));
		mav.addObject("id",id);
		mav.addObject("miembroCensDto",new MiembroCensDto());
		return mav;
	}
}
