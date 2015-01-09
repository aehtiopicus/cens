package com.aehtiopicus.cens.controller.cens;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;
import com.aehtiopicus.cens.mapper.cens.MiembroCensMapper;
import com.aehtiopicus.cens.service.cens.MiembroCensService;

@Controller
public class MiembroCensRestController extends AbstractRestController{

	@Autowired
	private MiembroCensService miembroCensService;
	@Autowired
	private MiembroCensMapper miembroCensMapper;
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS, method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public List<MiembroCensDto> crearMiembro(@RequestBody List<MiembroCensDto> miembroCensDto) throws Exception{
		
		List<MiembroCens> miembroCensList = miembroCensService.saveMiembroSens(miembroCensMapper.convertMiembrCensDtoListToEntityList(miembroCensDto));
		return miembroCensMapper.convertMiembrCensListToDtoList(miembroCensList);
	}
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS, method=RequestMethod.GET, produces="application/json", consumes="application/json")
	public List<MiembroCensDto> listMiembro() throws Exception{
		
		List<MiembroCens> miembroCensList =miembroCensService.listMiembrosCens();
		return miembroCensMapper.convertMiembrCensListToDtoList(miembroCensList);
		
	}
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS+"/{id}", method=RequestMethod.GET, produces="application/json", consumes="application/json")
	public MiembroCensDto getMiembro(@PathVariable(value="id") Long miembroId) throws Exception{
		
		MiembroCens miembroCens = miembroCensService.getMiembroCens(miembroId);
		return miembroCensMapper.convertMiembroCensToDto(miembroCens);
		
	}
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.MIEMBRO_CENS+"/{id}", method=RequestMethod.PUT, produces="application/json", consumes="application/json")
	public MiembroCensDto updateMiembro(@RequestBody MiembroCensDto miembroCensDto,@PathVariable(value="id") Long miembroId) throws Exception{
		
		miembroCensDto.setId(miembroId);
		List<MiembroCens> miembroCens = miembroCensService.saveMiembroSens(Arrays.asList(miembroCensMapper.convertMiembroCensDtoToEntity(miembroCensDto)));
		return miembroCensMapper.convertMiembroCensToDto(miembroCens.get(0));
		
	}
	
}
