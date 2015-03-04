package com.aehtiopicus.cens.controller.cens;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.dto.cens.ContactoDto;
import com.aehtiopicus.cens.mapper.cens.ContactoCensMapper;
import com.aehtiopicus.cens.service.cens.ContactoCensService;

@Controller
public class ContactoCensRestController extends AbstractRestController{

	@Autowired
	private ContactoCensMapper contactoMapper;
	
	@Autowired
	private ContactoCensService contactoCensService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.MIEMBRO_CONTACTO_EMAIL_REST}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ContactoDto createMiembroContactoEmail(@RequestBody List<ContactoDto> contacto ) throws Exception{
		
	}
}
