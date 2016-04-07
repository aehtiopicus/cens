package com.aehtiopicus.cens.controller.cens;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.mapper.cens.ProgramaCensMapper;
import com.aehtiopicus.cens.service.cens.ProgramaCensService;
import com.aehtiopicus.cens.utils.CensException;

@Controller
public class ProgramaCensRestController extends AbstractRestController{

	@Autowired
	private ProgramaCensValidator programaCensValidator;
	
	@Autowired
	private ProgramaCensService programaCensService;
	
	@Autowired
	private ProgramaCensMapper mapper;
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_REST, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgramaDto uploadPrograma(@PathVariable(value="id")Long asignaturaId,@RequestPart("properties")  ProgramaDto programaDto, @RequestPart(value="file",required=true)   MultipartFile file) throws Exception{		

		programaCensValidator.validate(programaDto, file); 
		programaDto.setAsignaturaId(asignaturaId);
		Programa programa = mapper.convertProgramaDtoToEntity(programaDto);
		EstadoRevisionType e = programa.getEstadoRevisionType();
		programa = programaCensService.savePrograma(programa,file);
		checkCambioEstado(programa,e);
		return mapper.convertProgramaToDto(programa);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_REST+"/{programaId}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgramaDto updatePrograma(@PathVariable(value="id")Long asignaturaId,@PathVariable(value="programaId")Long programaId,@RequestPart("properties")  ProgramaDto programaDto, @RequestPart(value="file",required=true)   MultipartFile file) throws Exception{		

		programaCensValidator.validate(programaDto, file);
		programaDto.setId(programaId);
		programaDto.setAsignaturaId(asignaturaId);		
		Programa programa = mapper.convertProgramaDtoToEntity(programaDto);
		EstadoRevisionType e = programa.getEstadoRevisionType();
		programa = programaCensService.savePrograma(programa,file);
		checkCambioEstado(programa,e);
		return mapper.convertProgramaToDto(programa);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_NO_FILE_REST, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgramaDto uploadPrograma(@PathVariable(value="id")Long asignaturaId,@RequestBody  ProgramaDto programaDto)  throws Exception{		

		programaCensValidator.validate(programaDto, null); 
		programaDto.setAsignaturaId(asignaturaId);
		Programa programa = mapper.convertProgramaDtoToEntity(programaDto);
		EstadoRevisionType e = programa.getEstadoRevisionType();
		programa = programaCensService.savePrograma(programa,null);
		checkCambioEstado(programa,e);
		return mapper.convertProgramaToDto(programa);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_NO_FILE_REST+"/{programaId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgramaDto updatePrograma(@PathVariable(value="id")Long asignaturaId,@PathVariable(value="programaId")Long programaId,@RequestBody  ProgramaDto programaDto) throws Exception{		

		programaCensValidator.validate(programaDto, null);
		programaDto.setId(programaId);
		programaDto.setAsignaturaId(asignaturaId);		
		Programa programa = mapper.convertProgramaDtoToEntity(programaDto);
		EstadoRevisionType e = programa.getEstadoRevisionType();
		programa = programaCensService.savePrograma(programa,null);
		checkCambioEstado(programa,e);
		return mapper.convertProgramaToDto(programa);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_REST+"/{programaId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProgramaDto getPrograma(@PathVariable(value="id")Long asignaturaId,@PathVariable(value="programaId")Long programaId) throws Exception{				
		
		return mapper.convertProgramaToDto(programaCensService.findById(programaId));        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_FILE_REST, method = RequestMethod.GET)
	public void downloadPrograma(@PathVariable Long programaId, HttpServletResponse response) throws CensException {
		Programa p = programaCensService.findById(programaId);
		if(p.getFileInfo()!=null){
			try{
				OutputStream baos =  response.getOutputStream();
				programaCensService.getArchivoAdjunto(p.getFileInfo().getFileLocationPath()+p.getFileInfo().getRealFileName(),baos);
	    		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	    		response.setHeader("Content-Disposition", "attachment; filename="+p.getFileInfo().getFileName());
			}catch(IOException e){
				throw new CensException("Error al obtener la cadena de salida");
			}
		}
	    
	    
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_FILE_REST, method = RequestMethod.DELETE)
	public @ResponseBody RestSingleResponseDto deletePrograma(@PathVariable Long programaId) throws CensException {
		Programa p = programaCensService.findById(programaId);
		programaCensService.removePrograma(p);
		p.setEstadoRevisionType(EstadoRevisionType.NUEVO);
		checkCambioEstado(p,EstadoRevisionType.INEXISTENTE);
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(programaId);
		dto.setMessage("Programa Eliminado ");
		return dto;
	    
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.PROGRAMA_CENS_REST+"/{programaId}/estado", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto updateProgramaEstado(@PathVariable(value="id")Long asignaturaId,@PathVariable(value="programaId")Long programaId,@RequestBody  ProgramaDto programaDto, HttpServletRequest request) throws Exception{		

		programaCensValidator.validateCambioEstado(programaDto.getEstadoRevisionType());
		Programa p = programaCensService.findById(programaId);
		EstadoRevisionType e = p.getEstadoRevisionType();
		p.setEstadoRevisionType(programaDto.getEstadoRevisionType());
		checkCambioEstado(p,e);		
		
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(programaId);
		dto.setMessage("Estado actualizado correctamente");
		programaCensService.removeSocialShare(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(),programaId);
		
		return dto;
	}
	
	private void checkCambioEstado(Programa p, EstadoRevisionType estadoAnterior){
		if(!p.getEstadoRevisionType().equals(estadoAnterior)){
			programaCensService.updateProgramaStatus(p, p.getEstadoRevisionType());
		}
	}
}
