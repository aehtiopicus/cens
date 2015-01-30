package com.aehtiopicus.cens.controller.cens;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.ComentarioDescriptionDto;
import com.aehtiopicus.cens.dto.cens.ComentarioRequestDto;
import com.aehtiopicus.cens.dto.cens.ComentarioRequestWrapperDto;
import com.aehtiopicus.cens.dto.cens.ComentariosDto;
import com.aehtiopicus.cens.mapper.cens.ComentarioCensMapper;
import com.aehtiopicus.cens.service.cens.ComentarioCensService;
import com.aehtiopicus.cens.utils.CensException;

@Controller
public class ComentarioCensRestController extends AbstractRestController{

	@Autowired
	private ComentarioCensMapper mapper;
	
	@Autowired
	private ComentarioCensService comentarioCensService;
	

	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.COMENTARIO_CENS_REST, method=RequestMethod.GET)
	public ComentariosDto listComentarios(@RequestParam("value") ComentarioRequestWrapperDto wrapperDto) throws Exception{				
	   ComentarioRequestDto comentarioRequestDto = wrapperDto.getDto();		
	   List<ComentarioCens> comentarioList = comentarioCensService.findAllParentcomments(comentarioRequestDto.getTipoId(),comentarioRequestDto.getTipoType());
	   ComentariosDto dto = mapper.createCommentariosDto(comentarioRequestDto.getUsuarioId(),findMiembroCens(comentarioRequestDto));
	   mapper.addComments(dto, comentarioList);
		return dto;
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.COMENTARIO_CENS_REST}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ComentarioDescriptionDto uploadComentario(@RequestPart("comentarioRequest") ComentarioRequestDto comentarioRequestDto,
			@RequestPart(value="file",required=true)   MultipartFile file) throws Exception{	
		
		return save(comentarioRequestDto,file,null);       
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.COMENTARIO_CENS_REST+"/{comentarioId}"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ComentarioDescriptionDto uploadComentarioPut(@PathVariable("comentarioId") Long comentarioId, 
			@RequestPart("comentarioRequest") ComentarioRequestDto comentarioRequestDto,
			@RequestPart(value="file",required=true)   MultipartFile file) throws Exception{	
		
		return save(comentarioRequestDto,file,comentarioId);       
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.COMENTARIO_CENS_NO_FILE_REST}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ComentarioDescriptionDto uploadComentarioSinArchivo(@RequestBody ComentarioRequestDto comentarioRequestDto) throws Exception{		

		return save(comentarioRequestDto,null,null);     
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.COMENTARIO_CENS_NO_FILE_REST+"/{comentarioId}"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ComentarioDescriptionDto uploadComentarioSinArchivoPut(@PathVariable("comentarioId") Long comentarioId, 
			@RequestBody ComentarioRequestDto comentarioRequestDto) throws Exception{		

		return save(comentarioRequestDto,null,comentarioId);     
	}
	
	
	
	private ComentarioDescriptionDto save(ComentarioRequestDto comentarioRequestDto,MultipartFile file, Long comentarioId) throws CensException{
		
		ComentarioCens cc = mapper.getDataForComentarioCens(comentarioRequestDto);
		cc.setId(comentarioId);
		MiembroCens mc= findMiembroCens(comentarioRequestDto);
		cc.setFullName(mc.getApellido().toUpperCase()+", "+mc.getNombre()+" ("+mc.getDni()+")");
		cc = comentarioCensService.saveComentario(cc,file);
		
		ComentarioDescriptionDto dto =mapper.mapSingleComentario(cc);
		if(comentarioId!=null){
			dto.setComment_id_original(comentarioId);
		}
		return dto;
		
	
	}
	
	private MiembroCens findMiembroCens(ComentarioRequestDto comentarioRequestDto)throws CensException{
		return comentarioCensService.getMiembroCensByPerfilTypeAndRealId(comentarioRequestDto.getUsuarioTipo(), comentarioRequestDto.getUsuarioId());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.COMENTARIO_CENS_REST+"/{comentarioId}"}, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ComentarioDescriptionDto delete(@PathVariable("comentarioId") Long comentarioId) throws Exception{	
		ComentarioDescriptionDto dto = new ComentarioDescriptionDto();
				
		comentarioCensService.delete(comentarioId);
		dto.setSuccess(true);
		return dto;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.COMENTARIO_CENS_REST+"/{id}/attachment", method = RequestMethod.GET)
	public void downloadArchivoComentario(@PathVariable("id") Long comentarioId, HttpServletResponse response) throws CensException {
		ComentarioCens cc = comentarioCensService.findById(comentarioId);
		if(cc.getFileCensInfo()!=null){
			try{
				OutputStream baos =  response.getOutputStream();
				comentarioCensService.getArchivoAdjunto(cc.getFileCensInfo().getFileLocationPath()+cc.getFileCensInfo().getRealFileName(),baos);
	    		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	    		response.setHeader("Content-Disposition", "attachment; filename="+cc.getFileCensInfo().getFileName());
			}catch(IOException e){
				throw new CensException("Error al obtener la cadena de salida");
			}
		}
	    
	    
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {UrlConstant.COMENTARIO_CENS_REST+"/{id}/attachment"}, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ComentarioDescriptionDto deleteAttachment(@PathVariable("id") Long comentarioId) throws Exception{	
						
		ComentarioCens cc = comentarioCensService.deleteAttachment(comentarioId);		
		ComentarioDescriptionDto dto =mapper.mapSingleComentario(cc);
		dto.setSuccess(true);
		dto.setComment_id(comentarioId);
		return dto;
	}
	
	
}
