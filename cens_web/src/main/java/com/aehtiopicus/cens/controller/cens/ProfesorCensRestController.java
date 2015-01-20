package com.aehtiopicus.cens.controller.cens;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.ProfesorCensValidator;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.ProfesorAsignaturaDto;
import com.aehtiopicus.cens.dto.cens.ProfesorDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.ProfesorCensMapper;
import com.aehtiopicus.cens.service.cens.ProfesorCensService;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class ProfesorCensRestController extends AbstractRestController{

	private static final Logger logger = LoggerFactory.getLogger(ProfesorCensRestController.class);
	
	@Autowired
	private ProfesorCensService profesorCensService;		
	
	@Autowired
	private ProfesorCensValidator profesorValidator;
	
	@Autowired
	private ProfesorCensMapper profesorCensMapper;
			
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.PROFESOR_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<ProfesorDto> listProfesores(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
		logger.info("listando profesores");
		RestRequest rr = getRequestRequest(wrapper);
		List<Profesor> profesorList = profesorCensService.listProfesores(rr);
		long cantidad  = profesorCensService.getTotalProfesoresFilterByProfile(rr);
		List<ProfesorDto> pDto = profesorCensMapper.convertProfesorListToDtoList(profesorList);						
		return convertToResponse(rr, cantidad, pDto);
		
	}
	
	private RestResponseDto<ProfesorDto> convertToResponse(RestRequest rr,long cantidad,List<ProfesorDto> mcDto){
		RestResponseDto<ProfesorDto> result = new RestResponseDto<ProfesorDto>();
	
		result.setTotal(Utils.getNumberOfPages(rr.getRow(),(int)cantidad));
		result.setPage(rr.getPage()+1);
		result.setRows(mcDto);
		result.setRecords((int)cantidad);
		result.setSord(rr.getSord());
		
		return result;
	}
	
	@ResponseStatus(HttpStatus.OK)	
	@RequestMapping(value = UrlConstant.PROFESOR_CENS_REMOVE_ASIGNATURAS_REST, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto removeAsignaturas(@PathVariable(value="id") Long profesorId) throws Exception{	
		profesorCensService.removeAsignaturasProfesor(profesorId);		
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(profesorId);
		dto.setMessage("Profesor eliminado de asignaturas");
		return dto;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.PROFESOR_CENS_CURSO_ASIGNATURAS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ProfesorAsignaturaDto listProfesoresAsignatura(@PathVariable(value="id")Long profesorId)  throws Exception{					  
		logger.info("listando asignaturas de profesor");
		Profesor profesor = profesorCensService.findById(profesorId);
		List<Curso> cursoList = profesorCensService.listCursoAsignaturaByProfesor(profesor);
		ProfesorAsignaturaDto dto =profesorCensMapper.convertCursoListIntoProfesorAsignaturaDto(cursoList,profesor);
		return dto;
		
	}
}
