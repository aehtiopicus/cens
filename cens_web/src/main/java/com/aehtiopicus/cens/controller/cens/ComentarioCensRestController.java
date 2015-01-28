package com.aehtiopicus.cens.controller.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.ComentarioRequestDto;
import com.aehtiopicus.cens.dto.cens.ComentarioRequestWrapperDto;
import com.aehtiopicus.cens.dto.cens.ComentariosDto;
import com.aehtiopicus.cens.dto.cens.ProgramaDto;
import com.aehtiopicus.cens.mapper.cens.ComentarioCensMapper;
import com.aehtiopicus.cens.service.cens.ComentarioCensService;

@Controller
public class ComentarioCensRestController extends AbstractRestController{

	@Autowired
	private ComentarioCensMapper mapper;
	
	@Autowired
	private ComentarioCensService comentarioCensService;
	

	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.COMENTARIO_CENS_REST, method=RequestMethod.GET)
	public ComentariosDto listComentarios(@RequestParam("value") ComentarioRequestWrapperDto wrapperDto,@PathVariable("id") Long comentarioId ) throws Exception{					  
	   ComentarioRequestDto comentarioRequestDto = wrapperDto.getDto();
		MiembroCens mc = comentarioCensService.getMiembroCensByPerfilTypeAndRealId(comentarioRequestDto.getUsuarioTipo(), comentarioRequestDto.getUsuarioId());
		ComentariosDto dto = mapper.createCommentariosDto(comentarioRequestDto.getUsuarioId(),mc);
		return dto;
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.COMENTARIO_CENS_REST,UrlConstant.COMENTARIO_CENS_REST+"/{comentarioId}"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgramaDto uploadPrograma(@PathVariable("id") Long comentarioId, 
			@RequestPart("comentarioRequest") ComentarioRequestDto comentarioRequestDto,
			@RequestPart("properties")  String programaDto, @RequestPart(value="file",required=true)   MultipartFile file) throws Exception{		

		return null;       
	}
}
