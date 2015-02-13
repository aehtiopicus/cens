package com.aehtiopicus.cens.controller.cens.mvc;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.utils.PageNotFoundException;

@Controller
public class ErrorController extends AbstractController{


	@RequestMapping(value="/errors/404", method=RequestMethod.GET)
	public ModelAndView pageNotFound(HttpServletResponse response) throws PageNotFoundException{
		ModelAndView mav =new ModelAndView("redirect:/mvc/displayError");
		mav.addObject("code", HttpStatus.NOT_FOUND.value());
		return mav;
	}
	
	@RequestMapping(value={"/errors/401"}, method=RequestMethod.GET)
	public ModelAndView unauthorized(HttpServletResponse response) throws PageNotFoundException{
		ModelAndView mav =new ModelAndView("redirect:/mvc/displayError");
		mav.addObject("code", HttpStatus.UNAUTHORIZED.value());
		return mav;
	}
	
	@RequestMapping(value="/mvc/displayError", method=RequestMethod.GET)
	public ModelAndView displayError(@RequestParam("code")int code) {
		ModelAndView mav = new ModelAndView(VistasConstant.MAIN);
		switch(HttpStatus.valueOf(code)){
		case NOT_FOUND:			
			mav.setViewName(VistasConstant.NOT_FOUND);
			break;
		case UNAUTHORIZED:
			mav.setViewName(VistasConstant.UNAUTHORIZED);
			break;
		default:
			break;
		
		}		
	    return mav;
	}
	
}
