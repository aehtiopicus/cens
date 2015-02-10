package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.mapper.cens.MaterialDidacticoCensMapper;
import com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService;

@Controller
public class MaterialDidacticoCensController extends AbstractController{

	@Autowired
	private MaterialDidacticoCensMapper materialDidacticoCensMapper;
	
	@Autowired
	private MaterialDidacticoCensService materialDidacticoCensService;
	
	
	@RequestMapping(value=UrlConstant.MATERIAL_DIDACTICO_CENS_MVC, method= RequestMethod.GET)
	public ModelAndView getMaterialDidacticoMainPage(Model model, Principal principal, @RequestParam("asignatura") String asigntaura, @PathVariable("id")Long programaId){
		ModelAndView mav = new ModelAndView(VistasConstant.MATERIAL_LIST_VIEW);				
		//check model
		mav.addObject("asignatura",asigntaura);
		mav.addObject("programaId", programaId);		
		return mav;
	}
	
	
	@RequestMapping(value=UrlConstant.MATERIAL_DIDACTICO_CENS_ABM_MVC+"/{materialId}", method= RequestMethod.GET)
	public ModelAndView getMaterialDidacticoABM(Model model, Principal principal, @RequestParam("asignatura") String asigntaura, 
			@PathVariable("programaId")Long programaId, @PathVariable("materialId") Long materialId,@RequestParam("nro") Integer nro){
		ModelAndView mav = new ModelAndView(VistasConstant.MATERIAL_ABM_VIEW);				
		//check model
		mav.addObject("asignatura",asigntaura);
		mav.addObject("programaId", programaId);
		mav.addObject("materialId",materialId);
		mav.addObject("nro",nro);
		mav.addObject("division",materialDidacticoCensMapper.convertToDto(materialDidacticoCensService.listDivisionPeriodo()));
		return mav;
	}
	
	@RequestMapping(value=UrlConstant.MATERIAL_DIDACTICO_CENS_ABM_MVC, method= RequestMethod.GET)
	public ModelAndView getMaterialDidacticoAlta(Model model, Principal principal, @RequestParam("asignatura") String asigntaura, 
			@PathVariable("programaId")Long programaId,@RequestParam("nro") Integer nro){
		ModelAndView mav = new ModelAndView(VistasConstant.MATERIAL_ABM_VIEW);				
		//check model
		mav.addObject("asignatura",asigntaura);
		mav.addObject("programaId", programaId);
		mav.addObject("nro",nro);
		mav.addObject("division",materialDidacticoCensMapper.convertToDto(materialDidacticoCensService.listDivisionPeriodo()));
		return mav;
	}
}
