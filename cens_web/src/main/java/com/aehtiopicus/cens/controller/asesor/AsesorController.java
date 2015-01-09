package com.aehtiopicus.cens.controller.asesor;



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
import com.aehtiopicus.cens.controller.AbstractController;

@Controller
public class AsesorController extends AbstractController{

	private static final Logger log = LoggerFactory.getLogger(AsesorController.class);
	

	
	@RequestMapping(value= UrlConstant.ASESOR_LIST_URL, method = RequestMethod.GET)
	public ModelAndView listAsesores(Principal principal, HttpServletRequest request, HttpServletResponse response, Model model){
		log.info("Usuario Form -> Nuevo");
        
        ModelAndView mav = new ModelAndView(VistasConstant.ASESORES_LIST_VIEW);
//        mav.addObject("usuarioDto", new UsuarioDto());
//        
//        mav.addObject("rolDtoList", rolMapper.convertRolTypeToRolDTO(rolService.listRol()));
        
        return mav;
	}
}
