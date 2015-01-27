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
import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.AsesorDashboardDto;
import com.aehtiopicus.cens.dto.cens.AsesorDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.mapper.cens.AsesosorCensMapper;
import com.aehtiopicus.cens.service.cens.AsesorCensService;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class AsesorCensRestController extends AbstractRestController{

	private static final Logger logger = LoggerFactory.getLogger(AsesorCensRestController.class);
	
	@Autowired
	private AsesorCensService asesorCensService;
	
	@Autowired
	private AsesosorCensMapper mapper;
	
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.ASESOR_CENS_CURSO_ASIGNATURAS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public AsesorDashboardDto listAsesorAsignatura(@PathVariable(value="id")Long asesorId)  throws Exception{					  
		logger.info("listando asignaturas activas del cens");		
		List<Curso> cursoList = asesorCensService.listCursos();
		List<Programa> programaList =asesorCensService.listProgramas();
		AsesorDashboardDto dto = mapper.convertToAsesorDashboardDto(cursoList,asesorId,programaList);		
		return dto;
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.ASESOR_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<AsesorDto> listProfesores(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
		logger.info("listando asesores");
		RestRequest rr = getRequestRequest(wrapper);
		List<Asesor> asesorList = asesorCensService.listAsesores(rr);
		long cantidad  = asesorCensService.getTotalProfesoresFilterByProfile(rr);
		List<AsesorDto> pDto = mapper.convertAsesorListToDtoList(asesorList);						
		return convertToResponse(rr, cantidad, pDto);
		
	}
	
	private RestResponseDto<AsesorDto> convertToResponse(RestRequest rr,long cantidad,List<AsesorDto> mcDto){
		RestResponseDto<AsesorDto> result = new RestResponseDto<AsesorDto>();
	
		result.setTotal(Utils.getNumberOfPages(rr.getRow(),(int)cantidad));
		result.setPage(rr.getPage()+1);
		result.setRows(mcDto);
		result.setRecords((int)cantidad);
		result.setSord(rr.getSord());
		
		return result;
	}
}
