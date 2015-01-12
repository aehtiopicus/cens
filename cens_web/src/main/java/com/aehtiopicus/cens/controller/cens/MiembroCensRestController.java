package com.aehtiopicus.cens.controller.cens;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.mapper.cens.MiembroCensMapper;
import com.aehtiopicus.cens.service.cens.MiembroCensService;

@Controller
public class MiembroCensRestController extends AbstractRestController{
		
	@Autowired
	private MiembroCensService miembroCensService;
	@Autowired
	private MiembroCensMapper miembroCensMapper;
		
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST, method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public List<MiembroCensDto> crearMiembro(@RequestBody List<MiembroCensDto> miembroCensDto) throws Exception{
		
		List<MiembroCens> miembroCensList = miembroCensService.saveMiembroSens(miembroCensMapper.convertMiembrCensDtoListToEntityList(miembroCensDto));
		return miembroCensMapper.convertMiembrCensListToDtoList(miembroCensList);
	}
	
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST, method=RequestMethod.GET, produces="application/json")
	public RestResponseDto<MiembroCensDto> listMiembro(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
	   
		RestRequest rr = getRequestRequest(wrapper);
		List<MiembroCens> miembroCensList = miembroCensService.listMiembrosCens(rr);
		long cantidad  = miembroCensService.getTotalUsersFilterByProfile(rr);
		List<MiembroCensDto> mcDto = miembroCensMapper.convertMiembrCensListToDtoList(miembroCensList);						
		return convertToResponse(rr, cantidad, mcDto);
		
	}
	
	private RestResponseDto<MiembroCensDto> convertToResponse(RestRequest rr,long cantidad,List<MiembroCensDto> mcDto){
		RestResponseDto<MiembroCensDto> result = new RestResponseDto<MiembroCensDto>();
		result.setCantidad(cantidad);
		result.setPage(rr.getPage()+1);
		result.setResponse(mcDto);
		result.setRow(rr.getRow());
		result.setSord(rr.getSord());
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST+"/{id}", method=RequestMethod.GET, produces="application/json", consumes="application/json")
	public MiembroCensDto getMiembro(@PathVariable(value="id") Long miembroId) throws Exception{
		
		MiembroCens miembroCens = miembroCensService.getMiembroCens(miembroId);
		return miembroCensMapper.convertMiembroCensToDto(miembroCens);
		
	}
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST+"/{id}", method=RequestMethod.PUT, produces="application/json", consumes="application/json")
	public MiembroCensDto updateMiembro(@RequestBody MiembroCensDto miembroCensDto,@PathVariable(value="id") Long miembroId) throws Exception{
		
		miembroCensDto.setId(miembroId);
		List<MiembroCens> miembroCens = miembroCensService.saveMiembroSens(Arrays.asList(miembroCensMapper.convertMiembroCensDtoToEntity(miembroCensDto)));
		return miembroCensMapper.convertMiembroCensToDto(miembroCens.get(0));
		
	}
	
}
