package com.aehtiopicus.cens.controller.cens;

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

@ControllerAdvice
public class AbstractRestController {
	private static final Logger logger = Logger
			.getLogger(AbstractRestController.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class })
	public @ResponseBody ErrorDto handleFormException(Exception ex) {
		logger.error(ex.getMessage());
		ex.printStackTrace();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setStatusCode(HttpStatus.BAD_REQUEST);
		errorDto.setErrorMessage(ex.getMessage());
		return errorDto;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ CensException.class })
	public @ResponseBody ErrorDto handleFormCensException(CensException ex) {
		logger.error(ex.getMessage());
		ErrorDto errorDto = new ErrorDto();
		errorDto.setStatusCode(HttpStatus.BAD_REQUEST);
		errorDto.setErrorMessage(ex.getMessage());
		return errorDto;

	}
}
