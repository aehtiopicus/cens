package com.aehtiopicus.cens.controller.cens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.AsignaturaDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.AsignaturaCensMapper;
import com.aehtiopicus.cens.service.cens.AsignaturaCensService;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class AsignaturaCensRestController extends AbstractRestController{

	@Autowired
	private AsignaturaCensService asignaturaCensService;
	@Autowired
	private AsignaturaCensMapper asignaturaCensMapper;
	@Autowired
	private AsignaturaCensValidator validator;
	
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
	
	private RestResponseDto<AsignaturaDto> convertToResponse(RestRequest rr,long cantidad,List<AsignaturaDto> mcDto){
		RestResponseDto<AsignaturaDto> result = new RestResponseDto<AsignaturaDto>();
	
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
}
