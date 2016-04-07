package com.aehtiopicus.cens.controller.cens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.CursoValiator;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.CursoDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.CursoCensMapper;
import com.aehtiopicus.cens.service.cens.CursoCensService;
import com.aehtiopicus.cens.util.Utils;

@Controller
//@Secured("ROLE_PRECEPTOR")
public class CursoCensRestController extends AbstractRestController{

	@Autowired
	private CursoCensMapper mapper;
	@Autowired
	private CursoValiator validator;
	@Autowired
	private CursoCensService cursoCensService;
	
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value=UrlConstant.CURSO_CENS_REST, method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CursoDto> createCurso(@RequestBody List<CursoDto> cursoDtoList) throws Exception{	
		List<Curso> cursoList = mapper.convertCursoDtoListToEntityList(cursoDtoList);
		validator.validate(cursoList);
		cursoList =cursoCensService.save(cursoList);
		return mapper.convertCursoListToDtoList(cursoList);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.CURSO_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<CursoDto> listCurso(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
	   
		RestRequest rr = getRequestRequest(wrapper);
		List<Curso> cursoList = cursoCensService.listCursos(rr);
		long cantidad  = cursoCensService.getTotalCursos(rr);
		List<CursoDto> mcDto = mapper.convertCursoListToDtoList(cursoList);					
		return convertToResponse(rr, cantidad, mcDto);
		
	}
	
	private RestResponseDto<CursoDto> convertToResponse(RestRequest rr,long cantidad,List<CursoDto> cDto){
		RestResponseDto<CursoDto> result = new RestResponseDto<CursoDto>();
	
		result.setTotal(Utils.getNumberOfPages(rr.getRow(),(int)cantidad));
		result.setPage(rr.getPage()+1);
		result.setRows(cDto);
		result.setRecords((int)cantidad);
		result.setSord(rr.getSord());
		
		return result;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.CURSO_CENS_REST+"/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public CursoDto getCurso(@PathVariable(value="id") Long cursoId) throws Exception{
		
		Curso curso = cursoCensService.getCurso(cursoId);
		return mapper.convertCursoToDto(curso);
		
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.CURSO_CENS_REST+"/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public CursoDto updateCurso(@RequestBody CursoDto cursoDto,@PathVariable(value="id") Long cursoId) throws Exception{		
		cursoDto.setId(cursoId);
		
		List<Curso> curso = new ArrayList<Curso>(Arrays.asList(mapper.convertCursoDtoToEntity(cursoDto)));	
		validator.validate(curso);
		 curso = cursoCensService.save(curso);
		
		return mapper.convertCursoToDto(curso.get(0));
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.CURSO_CENS_REST+"/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto deleteMiembro(@PathVariable(value="id") Long cursoId) throws Exception{		
		cursoCensService.deleteCurso(cursoId);		
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(cursoId);
		dto.setMessage("Curso Eliminado");
		return dto;
	}
}
