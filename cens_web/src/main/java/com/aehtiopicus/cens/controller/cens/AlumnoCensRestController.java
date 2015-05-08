package com.aehtiopicus.cens.controller.cens;

import java.util.List;
import java.util.Map;

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
import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.AlumnoDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.mapper.cens.AlumnoCensMapper;
import com.aehtiopicus.cens.service.cens.AlumnoCensService;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class AlumnoCensRestController extends AbstractRestController{

	private static final Logger logger = LoggerFactory.getLogger(AlumnoCensRestController.class);
	
	@Autowired
	private AlumnoCensMapper mapper;
	
	@Autowired
	private AlumnoCensService alumnoCensService;
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.ALUMNO_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<AlumnoDto> listAlumnos(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
		logger.info("listando alumnos");
		RestRequest rr = getRequestRequest(wrapper);
		List<Alumno> alumnoList = alumnoCensService.listAlumnos(rr);
		long cantidad  = alumnoCensService.getTotalAlumnoFilterByProfile(rr);
		List<AlumnoDto> pDto = mapper.convertAlumnoListToDtoList(alumnoList);						
		return convertToResponse(rr, cantidad, pDto);
		
	}
	
	private RestResponseDto<AlumnoDto> convertToResponse(RestRequest rr,long cantidad,List<AlumnoDto> mcDto){
		RestResponseDto<AlumnoDto> result = new RestResponseDto<AlumnoDto>();
	
		result.setTotal(Utils.getNumberOfPages(rr.getRow(),(int)cantidad));
		result.setPage(rr.getPage()+1);
		result.setRows(mcDto);
		result.setRecords((int)cantidad);
		result.setSord(rr.getSord());
		
		return result;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.ALUMNO_CENS_REST+"/{alumnoId}/asignaturas", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)	
	public void listAlumnosAsignatura(@PathVariable("alumnoId")Long alumnoId) throws Exception{					  
		logger.info("listando asignaturas de alumno");
		Map<Asignatura,Programa> iaList = alumnoCensService.listarAsignaturaAlumnoInscripto(alumnoId);
		mapper.convertInscripcionInto
//		RestRequest rr = getRequestRequest(wrapper);
//		List<Alumno> alumnoList = alumnoCensService.listAlumnos(rr);
//		long cantidad  = alumnoCensService.getTotalAlumnoFilterByProfile(rr);
//		List<AlumnoDto> pDto = mapper.convertAlumnoListToDtoList(alumnoList);						
//		return convertToResponse(rr, cantidad, pDto);
		
	}
}
