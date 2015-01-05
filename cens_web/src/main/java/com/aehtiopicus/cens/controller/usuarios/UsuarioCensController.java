package com.aehtiopicus.cens.controller.usuarios;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.AbstractController;

@Controller
public class UsuarioCensController extends AbstractController {
	
	@RequestMapping(value=UrlConstant.USUARIO_LIST_URL, method = RequestMethod.GET)
	public ModelAndView listUsuarios( HttpServletRequest request, HttpServletResponse response, Model model){
		return null;
	}

}
