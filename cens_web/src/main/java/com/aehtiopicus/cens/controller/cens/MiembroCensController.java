package com.aehtiopicus.cens.controller.cens;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.AbstractController;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;
import com.aehtiopicus.cens.mapper.cens.MiembroCensMapper;
import com.aehtiopicus.cens.service.cens.MiembroCensService;

@Controller
public class MiembroCensController extends AbstractController{

	@Autowired
	private MiembroCensService miembroCensService;
	@Autowired
	private MiembroCensMapper miembroCensMapper;
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS, method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public MiembroCensDto crearMiembro(@RequestBody MiembroCensDto miembroCensDto, Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		MiembroCens miembroCens= miembroCensService.saveMiembroSens(miembroCensMapper.convertMiembroCensDtoToEntity(miembroCensDto));
		return miembroCensMapper.convertMiembroCensToDto(miembroCens);
	}
	
}
