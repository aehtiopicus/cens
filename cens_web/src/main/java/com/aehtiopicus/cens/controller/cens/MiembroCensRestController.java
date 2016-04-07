package com.aehtiopicus.cens.controller.cens;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.MiembroCensValidator;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.MiembroCensMapper;
import com.aehtiopicus.cens.service.cens.MiembroCensService;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class MiembroCensRestController extends AbstractRestController{
		
	@Autowired
	private MiembroCensService miembroCensService;
	@Autowired
	private MiembroCensMapper miembroCensMapper;
	@Autowired
	private MiembroCensValidator validator;
			
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<MiembroCensDto> crearMiembro(@RequestBody List<MiembroCensDto> miembroCensDto) throws Exception{
		
		List<MiembroCens> miembroCensList = miembroCensMapper.convertMiembrCensDtoListToEntityList(miembroCensDto);
		validator.validate(miembroCensList);
		miembroCensList = miembroCensService.saveMiembroSens(miembroCensList);		
		return miembroCensMapper.convertMiembrCensListToDtoList(miembroCensList);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<MiembroCensDto> listMiembro(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
	   
		RestRequest rr = getRequestRequest(wrapper);
		List<MiembroCens> miembroCensList = new ArrayList<MiembroCens>(miembroCensService.listMiembrosCens(rr));
		long cantidad  = miembroCensService.getTotalUsersFilterByProfile(rr);
		
		Iterator<MiembroCens> mcIterator = miembroCensList.iterator();
		MiembroCens loggedMC = miembroCensService.getMiembroCensByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		while(mcIterator.hasNext()){
			if(mcIterator.next().getId().equals(loggedMC.getId())){
				mcIterator.remove();
				cantidad--;
			}
		}
		
		
		List<MiembroCensDto> mcDto = miembroCensMapper.convertMiembrCensListToDtoList(miembroCensList);						
		return convertToResponse(rr, cantidad, mcDto);
		
	}
	
	private RestResponseDto<MiembroCensDto> convertToResponse(RestRequest rr,long cantidad,List<MiembroCensDto> mcDto){
		RestResponseDto<MiembroCensDto> result = new RestResponseDto<MiembroCensDto>();
	
		result.setTotal(Utils.getNumberOfPages(rr.getRow(),(int)cantidad));
		result.setPage(rr.getPage()+1);
		result.setRows(mcDto);
		result.setRecords((int)cantidad);
		result.setSord(rr.getSord());
		
		return result;
	}
	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST+"/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public MiembroCensDto getMiembro(@PathVariable(value="id") Long miembroId) throws Exception{
		
		MiembroCens miembroCens = miembroCensService.getMiembroCens(miembroId);
		return miembroCensMapper.convertMiembroCensToDto(miembroCens);
		
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST+"/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public MiembroCensDto updateMiembro(@RequestBody MiembroCensDto miembroCensDto,@PathVariable(value="id") Long miembroId) throws Exception{		
		miembroCensDto.setId(miembroId);
		
		List<MiembroCens> miembroCens = new ArrayList<MiembroCens>(Arrays.asList(miembroCensMapper.convertMiembroCensDtoToEntity(miembroCensDto)));		
		validator.validate(miembroCens);
		 miembroCens = miembroCensService.saveMiembroSens(miembroCens);
		
		return miembroCensMapper.convertMiembroCensToDto(miembroCens.get(0));
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS_REST+"/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto deleteMiembro(@PathVariable(value="id") Long miembroId) throws Exception{		
		miembroCensService.deleteMiembro(miembroId);		
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(miembroId);
		dto.setMessage("Miembro Eliminado");
		return dto;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping( value = UrlConstant.MIEMBRO_CENS_REST_PICTURE, method = RequestMethod.GET )
	public void getPicturePreview( @PathVariable(value="id")Long miembroId,HttpServletRequest request,HttpServletResponse response ) throws Exception{
		RestTemplate rs = new RestTemplate();
		String url = null;
		if(request.getServerPort()!= 0){
			url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		}else{
			url = request.getScheme()+"://"+request.getServerName()+"/"+request.getContextPath();
		}
		 url = url+UrlConstant.USUARIO_CENS_REST_PICTURE.replace("{id}",""+ miembroCensService.getMiembroCens(miembroId).getUsuario().getId());

		ClientHttpRequest chr = rs.getRequestFactory().createRequest(new URI(url),HttpMethod.GET);
		
		IOUtils.copy(chr.execute().getBody(),response.getOutputStream());
		
	}
	
}
