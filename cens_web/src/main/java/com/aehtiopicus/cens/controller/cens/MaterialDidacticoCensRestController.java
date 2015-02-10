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
import com.aehtiopicus.cens.controller.cens.validator.MaterialDidacticoCensValidator;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.MaterialDidacticoDto;
import com.aehtiopicus.cens.dto.cens.RestRequestDtoWrapper;
import com.aehtiopicus.cens.dto.cens.RestResponseDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.MaterialDidacticoCensMapper;
import com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.utils.CensException;

@Controller
public class MaterialDidacticoCensRestController extends AbstractRestController{

	@Autowired
	private MaterialDidacticoCensService materialDidacticoCensService;
	
	@Autowired
	private MaterialDidacticoCensMapper mapper;
	
	@Autowired
	private MaterialDidacticoCensValidator materialDidacticoValidator;
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_REST, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public RestResponseDto<MaterialDidacticoDto> listMaterialDidactico(@RequestParam(value="requestData",required=false) RestRequestDtoWrapper wrapper) throws Exception{					  
	   
		RestRequest rr = getRequestRequest(wrapper);
		List<MaterialDidactico> materialDidacticoList = materialDidacticoCensService.listMaterialDidactico(rr);
		long cantidad  = materialDidacticoCensService.getTotalMaterial(rr);
		List<MaterialDidacticoDto> mdDto = mapper.convertMaterialDidacticoListToDtoList(materialDidacticoList);					
		return convertToResponse(rr, cantidad, mdDto);
		
	}
	
	private RestResponseDto<MaterialDidacticoDto> convertToResponse(RestRequest rr,long cantidad,List<MaterialDidacticoDto> mdDto){
		RestResponseDto<MaterialDidacticoDto> result = new RestResponseDto<MaterialDidacticoDto>();
	
		result.setTotal(Utils.getNumberOfPages(rr.getRow(),(int)cantidad));
		result.setPage(rr.getPage()+1);
		result.setRows(mdDto);
		result.setRecords((int)cantidad);
		result.setSord(rr.getSord());
		
		return result;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_REST, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MaterialDidacticoDto uploadMaterial(@RequestPart("properties")  MaterialDidacticoDto mdDto, @RequestPart(value="file",required=true)   MultipartFile file) throws Exception{		

		materialDidacticoValidator.validate(mdDto, file); 
		MaterialDidactico md = mapper.convertMaterialDidacticoDtoToEntity(mdDto);
		
		md = materialDidacticoCensService.saveMaterialDidactico(md,file);
		
		return mapper.convertMaterialDidacticoToDto(md);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_REST+"/{materialId}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MaterialDidacticoDto updateMaterialDidactico(@PathVariable(value="materialId")Long materialId,@RequestPart("properties")  MaterialDidacticoDto mdDto, @RequestPart(value="file",required=true)   MultipartFile file) throws Exception{		

		materialDidacticoValidator.validate(mdDto, file);
		mdDto.setId(materialId);	
		MaterialDidactico materialDidactico = mapper.convertMaterialDidacticoDtoToEntity(mdDto);
		
		materialDidactico = materialDidacticoCensService.saveMaterialDidactico(materialDidactico,file);
		
		return mapper.convertMaterialDidacticoToDto(materialDidactico);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_NO_FILE_REST, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MaterialDidacticoDto uploadPrograma(@RequestBody  MaterialDidacticoDto mdDto)  throws Exception{		

		materialDidacticoValidator.validate(mdDto, null); 
		MaterialDidactico materialDidactico = mapper.convertMaterialDidacticoDtoToEntity(mdDto);
		
		materialDidactico = materialDidacticoCensService.saveMaterialDidactico(materialDidactico,null);
		
		return mapper.convertMaterialDidacticoToDto(materialDidactico);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_NO_FILE_REST+"/{materialId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MaterialDidacticoDto updateMaterial(@PathVariable(value="materialId")Long materialId,@RequestBody  MaterialDidacticoDto mdDto) throws Exception{		

		materialDidacticoValidator.validate(mdDto, null);
		mdDto.setId(materialId);	
		MaterialDidactico materialDidactico = mapper.convertMaterialDidacticoDtoToEntity(mdDto);
		
		materialDidactico = materialDidacticoCensService.saveMaterialDidactico(materialDidactico,null);
		
		return mapper.convertMaterialDidacticoToDto(materialDidactico);        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_REST+"/{materialId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MaterialDidacticoDto getPrograma(@PathVariable(value="materialId")Long materialId) throws Exception{				
		
		return mapper.convertMaterialDidacticoToDto(materialDidacticoCensService.findById(materialId));        
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_FILE_REST, method = RequestMethod.GET)
	public void downloadMaterial(@PathVariable Long materialId, HttpServletResponse response) throws CensException {
		MaterialDidactico md = materialDidacticoCensService.findById(materialId);
		if(md.getFileInfo()!=null){
			try{
				OutputStream baos =  response.getOutputStream();
				materialDidacticoCensService.getArchivoAdjunto(md.getFileInfo().getFileLocationPath()+md.getFileInfo().getRealFileName(),baos);
	    		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	    		response.setHeader("Content-Disposition", "attachment; filename="+md.getFileInfo().getFileName());
			}catch(IOException e){
				throw new CensException("Error al obtener la cadena de salida");
			}
		}
	    
	    
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_FILE_REST, method = RequestMethod.DELETE)
	public @ResponseBody RestSingleResponseDto deleteCartilla(@PathVariable Long materialId) throws CensException {
		
		materialDidacticoCensService.removeMaterialDidactico(materialId);
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(materialId);
		dto.setMessage("Material Did&aactue;ctico Eliminado ");
		return dto;
	    
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_REST+"/{materialId}",method = RequestMethod.DELETE)
	public @ResponseBody RestSingleResponseDto deleteMaterialDidactico(@PathVariable("materialId") Long materialId ) throws Exception{
		
		materialDidacticoCensService.removeMaterialDidacticoCompleto(materialId);
		RestSingleResponseDto dto = new RestSingleResponseDto();
		dto.setId(materialId);
		dto.setMessage("Material Did&aactue;ctico Eliminado ");
		return dto;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = UrlConstant.MATERIAL_DIDACTICO_CENS_REST+"/{materialId}/estado", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RestSingleResponseDto updateMaterialDidacticoEstado(@PathVariable(value="materialId")Long materialId,@RequestBody  MaterialDidacticoDto mdDto) throws Exception{		

		materialDidacticoValidator.validateCambioEstado(mdDto.getEstadoRevisionType());
		
		materialDidacticoCensService.updateMaterialDidacticoStatus(materialId,mdDto.getEstadoRevisionType());		
		
		RestSingleResponseDto dto = new RestSingleResponseDto();	
		dto.setMessage("Estado actualizado correctamente");
		
		return dto;
	}
}
