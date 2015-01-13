package com.aehtiopicus.cens.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.dto.cens.ErrorDto;
import com.aehtiopicus.cens.utils.CensException;


public class AbstractController {


	 private static final Logger logger = Logger.getLogger(AbstractController.class);

//	@ExceptionHandler({Exception.class})
	public ModelAndView handleFormException(Exception ex) {
		logger.error(ex.getMessage());
		ex.printStackTrace();
		ModelAndView mv = new ModelAndView(VistasConstant.ERROR_VIEW);
		mv.addObject("error",ex.getMessage());
		return mv;
	}
//	@ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
//	@ExceptionHandler({CensException.class})
	public @ResponseBody ErrorDto handleFormCensException(CensException ex) {
		logger.error(ex.getMessage());
		ErrorDto errorDto = new ErrorDto();
		errorDto.setStatusCode(HttpStatus.BAD_REQUEST);
		errorDto.setMessage(ex.getMessage());
        return errorDto;
		
	}
	
}
