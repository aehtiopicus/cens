package com.aehtiopicus.cens.controller.cens.mvc;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.utils.PageNotFoundException;

@ControllerAdvice
public class AbstractController {


	 private static final Logger logger = Logger.getLogger(AbstractController.class);

	@ExceptionHandler({Exception.class})
	public ModelAndView handleFormException(Exception ex) {
		logger.error(ex.getMessage());
		ex.printStackTrace();
		ModelAndView mv = new ModelAndView(VistasConstant.ERROR_VIEW);
		mv.addObject("error",ex.getMessage());
		return mv;
	}
	
	@ExceptionHandler({PageNotFoundException.class})
	public ModelAndView handleFormException(PageNotFoundException ex) {		
		
		ModelAndView mv = new ModelAndView(VistasConstant.NOT_FOUND);
		mv.addObject("error",ex.getMessage());
		return mv;
	}
	
	
	
}

