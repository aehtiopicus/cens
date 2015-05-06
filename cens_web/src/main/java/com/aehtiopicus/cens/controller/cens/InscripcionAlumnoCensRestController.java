package com.aehtiopicus.cens.controller.cens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.controller.cens.validator.InscripcionAlumnoCensValidator;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.dto.cens.AsignaturaInscripcionDto;
import com.aehtiopicus.cens.dto.cens.AsignaturaInscripcionResultDto;
import com.aehtiopicus.cens.dto.cens.RestSingleResponseDto;
import com.aehtiopicus.cens.mapper.cens.InscripcionAlumnoCensMapper;
import com.aehtiopicus.cens.service.cens.InscripcionAlumnoCensService;

@Controller
public class InscripcionAlumnoCensRestController extends AbstractRestController{

	private static final Logger logger = LoggerFactory.getLogger(InscripcionAlumnoCensRestController.class);
	
	@Autowired
	private InscripcionAlumnoCensValidator validator;
	
	@Autowired
	private InscripcionAlumnoCensMapper mapper;
	
	@Autowired
	private InscripcionAlumnoCensService inscripcionAlumnoCensService;
	
	@ResponseBody
	@RequestMapping(value = UrlConstant.INSCRIPCION_ALUMNO_CENS_REST, method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AsignaturaInscripcionResultDto> inscribirAlumno(@PathVariable(value="asignaturaId")Long asignaturaId, @RequestBody AsignaturaInscripcionDto asignaturaInscripcionDto) throws Exception{
		
		logger.info("Inscribiendo alumnos");
		AsignaturaInscripcion ai = mapper.convertAsignaturaInscripcionDtoToEntityWrapper(asignaturaInscripcionDto);
		ai.getAsignatura().setId(asignaturaId);
		validator.validateInscripcion(ai);
		AsignaturaInscripcionResultDto airDto = mapper.convertAsignaturaInscripcionResultToDto(inscripcionAlumnoCensService.inscribirAlumnos(ai));
		ResponseEntity<AsignaturaInscripcionResultDto> re = null;		
		
		if(airDto.isInscripcionCompleteFailure()){
			re = new ResponseEntity<AsignaturaInscripcionResultDto>(airDto, HttpStatus.BAD_REQUEST);
		}else{
			re = new ResponseEntity<AsignaturaInscripcionResultDto>(airDto, HttpStatus.OK);
		}
		
		
		return re;
	}
	
	@ResponseBody
	@RequestMapping(value=UrlConstant.INSCRIPCION_ALUMNO_CENS_REST+"/alumno/{alumnoId}", method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
	public RestSingleResponseDto eliminarInscripcion(@PathVariable(value="asignaturaId") Long asignaturaId, @PathVariable(value="alumnoId") Long alumnoId) throws Exception{
		logger.info("Eliminando inscripcion");
		inscripcionAlumnoCensService.eliminarInscripcion(asignaturaId,alumnoId);
		
		RestSingleResponseDto rsrDto = new RestSingleResponseDto();
		rsrDto.setId(alumnoId);
		rsrDto.setMessage("Inscripci&oacute;n del Alumno eliminada");
		return rsrDto;
	}
}
