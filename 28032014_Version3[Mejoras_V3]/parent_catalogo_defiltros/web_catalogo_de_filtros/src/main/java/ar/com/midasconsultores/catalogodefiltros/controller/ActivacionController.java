package ar.com.midasconsultores.catalogodefiltros.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="activation")
public class ActivacionController {

	@RequestMapping(value="/{codigo}", method = RequestMethod.GET)
	public ModelAndView activationRequested(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value="codigo") String codigo){
		ModelAndView mav = new ModelAndView("activation");
		mav.addObject("codigo", codigo);
		return mav;		
	}
	
}
