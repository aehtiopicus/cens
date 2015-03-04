package com.aehtiopicus.cens.controller.cens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.ContactoCensValidator;
import com.aehtiopicus.cens.domain.entities.Contacto;
import com.aehtiopicus.cens.dto.cens.ContactoDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.ContactoCensMapper;
import com.aehtiopicus.cens.service.cens.ContactoCensService;

@Controller
public class ContactoCensRestController extends AbstractRestController{

	@Autowired
	private ContactoCensMapper contactoMapper;
	
	@Autowired
	private ContactoCensService contactoCensService;
	
	@Autowired
	private ContactoCensValidator contactoCensValidator;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.MIEMBRO_CONTACTO_EMAIL_REST}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ContactoDto> createMiembroContactoEmail(@RequestBody List<ContactoDto> dtoList ) throws Exception{
		contactoCensValidator.validate(dtoList);
		List<Contacto> contacto = contactoMapper.converContactoDtoToEntity(dtoList);
		contacto = contactoCensService.save(contacto);
		return contactoMapper.convertEntityToDto(contacto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.MIEMBRO_CONTACTO_EMAIL_REST+"/{emailId}"}, method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ContactoDto updateMiembroContactoEmail(@RequestBody ContactoDto dtoList, @PathVariable("emailId") Long emailId ) throws Exception{
		contactoCensValidator.validate(Arrays.asList(dtoList));
		List<Contacto> contacto = contactoMapper.converContactoDtoToEntity(Arrays.asList(dtoList));
		contacto = contactoCensService.save(contacto);
		return contactoMapper.convertEntityToDto(contacto.get(0));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.MIEMBRO_CONTACTO_EMAIL_REST+"/{emailId}"}, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto updateMiembroContactoEmail(@PathVariable("emailId") Long emailId) throws Exception{
		
		contactoCensService.deleteContacto(emailId);
		RestSingleResponseDto rsDto = new RestSingleResponseDto();
		rsDto.setId(emailId);
		rsDto.setMessage("Email eliminado");
		return rsDto;
	}
	
	/**
	 * No pagination 
	 * @param miembroId
	 * @return
	 * @throws Exception
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.MIEMBRO_CONTACTO_EMAIL_REST}, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ContactoDto> listAllMiembroContactoEmail(@PathVariable("miembroId") Long miembroId) throws Exception{
		
		List<Contacto> contactoList =contactoCensService.getContactos(miembroId);
		if(CollectionUtils.isEmpty(contactoList)){
			contactoList = new ArrayList<>();
		}
		return contactoMapper.convertEntityToDto(contactoList);		
	}
}
