package com.aehtiopicus.cens.controller.cens;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.ErrorDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.mapper.cens.RestRequestMapper;
import com.aehtiopicus.cens.utils.CensException;

@ControllerAdvice
public class AbstractRestController {
	private static final Logger logger = Logger
			.getLogger(AbstractRestController.class);
	
	private static final String DEFAUL_PAGE_PROPERTY = "#{restRequestProperties['rest.default.page']}";
	private static final String DEFAUL_ROW_PROPERTY = "#{restRequestProperties['rest.default.row']}";
	private static final String DEFAUL_SORT_PROPERTY = "#{restRequestProperties['rest.default.sort']}";

	
	@Autowired
	private RestRequestMapper restRequestMapper;
	
	
	@Value(DEFAUL_PAGE_PROPERTY)
	private Integer page;
	@Value(DEFAUL_ROW_PROPERTY)
	private Integer row;
	@Value(DEFAUL_SORT_PROPERTY)
	private String sort;

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class })
	public @ResponseBody ErrorDto handleFormException(Exception ex) {
		logger.error(ex.getMessage());
		ex.printStackTrace();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorDto(false);
		errorDto.setStatusCode(HttpStatus.BAD_REQUEST);
		errorDto.setMessage(ex.getMessage());
		return errorDto;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ CensException.class })
	public @ResponseBody ErrorDto handleFormCensException(CensException ex) {
		logger.error(ex.getMessage());
		ErrorDto errorDto = new ErrorDto();
		errorDto.setStatusCode(HttpStatus.BAD_REQUEST);
		errorDto.setMessage(ex.getMessage());
		errorDto.setErrors(ex.getError());
		return errorDto;

	}

	/**
	 * Convert json request into resRequest
	 * @param wrapper
	 * @return
	 */
	protected RestRequest getRequestRequest(RestRequestDtoWrapper wrapper){
		  RestRequestDto restRequestDto = null;
			
		    if(wrapper==null){
		    	restRequestDto = new RestRequestDto();
		    	restRequestDto.setPage(page);
		    	restRequestDto.setRow(row);
		    	restRequestDto.setSord(sort);
		    }else{
		    	restRequestDto = wrapper.getDto();
		    }
		return restRequestMapper.convertRestRequestDto(restRequestDto);
	}
	
	
}
