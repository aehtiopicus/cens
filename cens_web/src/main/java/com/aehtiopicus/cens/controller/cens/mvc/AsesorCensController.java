package com.aehtiopicus.cens.controller.cens.mvc;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.mapper.cens.MaterialDidacticoCensMapper;
import com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService;

@Controller
public class AsesorCensController extends AbstractController{

	@Autowired
	private MaterialDidacticoCensMapper materialDidacticoCensMapper;
	
	@Autowired
	private MaterialDidacticoCensService materialDidacticoCensService;
	
	@RequestMapping(value=UrlConstant.ASESOR_CENS_DASHBOARD_MVC, method= RequestMethod.GET)
	public ModelAndView getAsignaturasProfesor(HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.ASESOR_DASHBOARD_LIST_VIEW);				
		return mav;
	}
	
	@RequestMapping(value=UrlConstant.ASESOR_CENS_ASIGNATURA_MVC, method= RequestMethod.GET)
	public ModelAndView getAsignaturaRevision(@RequestParam("asignatura") String asignatura,
			@RequestParam("estado") EstadoRevisionType estado,
			@PathVariable(value="asignaturaId") Long asignaturaId,
			@PathVariable(value="id") Long asesorId,
			@PathVariable(value="programaId") Long programaId,
			HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.ASESOR_ASIGNATURA_VIEW);
		mav.addObject("asignatura",asignatura);
		mav.addObject("estado",estado);
		mav.addObject("asignaturaId",asignaturaId);
		mav.addObject("programaId",programaId);
		List<EstadoRevisionType> type = Arrays.asList(EstadoRevisionType.LISTO,EstadoRevisionType.ASIGNADO,EstadoRevisionType.CAMBIOS,EstadoRevisionType.ACEPTADO,EstadoRevisionType.RECHAZADO);
	
		mav.addObject("estadosPosibles",type);
		return mav;
	}
	
	@RequestMapping(value=UrlConstant.ASESOR_CENS_MATERIAL_MVC, method= RequestMethod.GET)
	public ModelAndView getMaterialRevision(@RequestParam("asignatura") String asignatura,
			@RequestParam("estado") EstadoRevisionType estado,
			@RequestParam("nro") int nro,
			@PathVariable(value="asignaturaId") Long asignaturaId,
			@PathVariable(value="id") Long asesorId,
			@PathVariable(value="programaId") Long programaId,
			@PathVariable(value="materialId") Long materialId,
			HttpServletRequest request, HttpServletResponse response, Principal principal, Model model){				
		ModelAndView mav = new ModelAndView(VistasConstant.ASESOR_MATERIAL_VIEW);
		mav.addObject("asignatura",asignatura);
		mav.addObject("estado",estado);
		mav.addObject("asignaturaId",asignaturaId);
		mav.addObject("programaId",programaId);
		mav.addObject("materialId",materialId);
		mav.addObject("nro",nro);
		mav.addObject("division",materialDidacticoCensMapper.convertToDto(materialDidacticoCensService.listDivisionPeriodo()));
		List<EstadoRevisionType> type = Arrays.asList(EstadoRevisionType.LISTO,EstadoRevisionType.ASIGNADO,EstadoRevisionType.CAMBIOS,EstadoRevisionType.ACEPTADO,EstadoRevisionType.RECHAZADO);
	
		mav.addObject("estadosPosibles",type);
		return mav;
	}
}
