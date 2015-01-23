package com.aehtiopicus.cens.controller.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.ProgramaCensValidator;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.dto.cens.ProgramaDto;
import com.aehtiopicus.cens.mapper.cens.ProgramaCensMapper;
import com.aehtiopicus.cens.service.cens.ProgramaCensService;

@Controller
public class ProgramaCensRestController extends AbstractRestController{

	@Autowired
	private ProgramaCensValidator programaCensValidator;
	
	@Autowired
	private ProgramaCensService programaCensService;
	
	@Autowired
	private ProgramaCensMapper mapper;
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.ASIGNATURA_PROGRAMA_CENS_REST, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgramaDto uploadPrograma(@PathVariable(value="id")Long asignaturaId,@RequestPart("properties")  ProgramaDto programaDto, @RequestPart(value="file",required=false)   MultipartFile file) throws Exception{		

		programaCensValidator.validate(programaDto, file); 
		programaDto.setAsignaturaId(asignaturaId);
		Programa programa = mapper.convertProgramaDtoToEntity(programaDto);
		
		programa = programaCensService.savePrograma(programa,file);
		
		return mapper.convertProgramaToDto(programa);        
	}
}
