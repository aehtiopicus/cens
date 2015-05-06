package com.aehtiopicus.cens.controller.cens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.AsignaturaCensValidator;
import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.AlumnoDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.AlumnoCensMapper;
import com.aehtiopicus.cens.mapper.cens.AsignaturaCensMapper;
import com.aehtiopicus.cens.service.cens.AlumnoCensService;
import com.aehtiopicus.cens.service.cens.AsignaturaCensService;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class AsignaturaCensRestController extends AbstractRestController{

	private static final Logger logger = LoggerFactory.getLogger(AsignaturaCensRestController.class);
	
	@Autowired
	private AsignaturaCensService asignaturaCensService;
	@Autowired
	private AsignaturaCensMapper asignaturaCensMapper;
	@Autowired
	private AsignaturaCensValidator validator;
	
	@Autowired
	private AlumnoCensMapper mapper;
	
	@Autowired
	private AlumnoCensService alumnoCensService;

	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.ASIGNATURA_CENS_REST, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<AsignaturaDto> crearAsignatura(@RequestBody List<AsignaturaDto> AsignaturaDto) throws Exception{
		
		List<Asignatura> asignaturaList = asignaturaCensMapper.convertAsignaturaDtoListToEntityList(AsignaturaDto);
		validator.validate(asignaturaList);
		asignaturaList = asignaturaCensService.saveAsignaturas(asignaturaList);		
		return asignaturaCensMapper.convertAsignaturaListToDtoList(asignaturaList);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.ASIGNATURA_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<AsignaturaDto> listAsignaturas(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
	   
		RestRequest rr = getRequestRequest(wrapper);
		List<Asignatura> asignaturaList = asignaturaCensService.listAsignaturas(rr);
		long cantidad  = asignaturaCensService.getTotalAsignaturasFilterByProfile(rr);
		List<AsignaturaDto> mcDto = asignaturaCensMapper.convertAsignaturaListToDtoList(asignaturaList);						
		return convertToResponse(rr, cantidad, mcDto);
		
	}
	
	private <T> RestResponseDto<T> convertToResponse(RestRequest rr,long cantidad,List<T> mcDto){
		RestResponseDto<T> result = new RestResponseDto<T>();
	
		result.setTotal(Utils.getNumberOfPages(rr.getRow(),(int)cantidad));
		result.setPage(rr.getPage()+1);
		result.setRows(mcDto);
		result.setRecords((int)cantidad);
		result.setSord(rr.getSord());
		
		return result;
	}
	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.ASIGNATURA_CENS_REST+"/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public AsignaturaDto getAsignatura(@PathVariable(value="id") Long asignaturaID) throws Exception{
		
		Asignatura asignatura = asignaturaCensService.getAsignatura(asignaturaID);
		return asignaturaCensMapper.convertAsignaturaToDto(asignatura);
		
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.ASIGNATURA_CENS_REST+"/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public AsignaturaDto updateAsignatura(@RequestBody AsignaturaDto AsignaturaDto,@PathVariable(value="id") Long asignaturaID) throws Exception{		
		AsignaturaDto.setId(asignaturaID);
		
		List<Asignatura> asignatura = new ArrayList<Asignatura>(Arrays.asList(asignaturaCensMapper.convertAsignaturaDtoToEntity(AsignaturaDto)));		
		validator.validate(asignatura);
		 asignatura = asignaturaCensService.saveAsignaturas(asignatura);
		
		return asignaturaCensMapper.convertAsignaturaToDto(asignatura.get(0));
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.ASIGNATURA_CENS_REST+"/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto deleteAsignatura(@PathVariable(value="id") Long asignaturaID) throws Exception{		
		asignaturaCensService.deleteAsignatura(asignaturaID);		
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(asignaturaID);
		dto.setMessage("Asignatura Eliminada");
		return dto;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.ASIGNATURA_ALUMNO_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<AlumnoDto> listAlumnosFromAsignatura(@PathVariable(value="asignaturaId") Long asignaturaId,@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
		logger.info("listando alumnos");
		RestRequest rr = getRequestRequest(wrapper);
		rr.getFilters().put("asignaturaId", asignaturaId.toString());
		List<Alumno> alumnoList = alumnoCensService.listAlumnos(rr);
		long cantidad  = alumnoCensService.getTotalAlumnoFilterByProfile(rr);
		List<AlumnoDto> pDto = mapper.convertAlumnoListToDtoList(alumnoList);
		Asignatura asignatura = asignaturaCensService.getAsignatura(asignaturaId);
		AsignaturaDto aDto =asignaturaCensMapper.convertAsignaturaToDto(asignatura);
		RestResponseDto<AlumnoDto> rrDto = convertToResponse(rr, cantidad, pDto);
		rrDto.setExtraData(aDto);
		return rrDto;
		
	}
	
	
	

	
	
}
