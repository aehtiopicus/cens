package ar.com.midasconsultores.catalogodefiltros.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="volcado")
public class VolcadoController {

	@RequestMapping(value={"","/"}, method = RequestMethod.GET)
	public ModelAndView activationRequested(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("volcado/volcado");
		return mav;
		
	}
}
