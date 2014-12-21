package com.aehtiopicus.cens.validator;

import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.dto.RelacionLaboralDto;
import com.aehtiopicus.cens.dto.RelacionPuestoEmpleadoDto;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.util.Utils;

@Service
public class RelacionLaboralValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RelacionLaboralDto.class.equals(clazz);
	}
	@Autowired
	private EmpleadoService empleadoService;
	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");

	
	public void setEmpleado(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	@Override
	public void validate(Object target, Errors errors) {
		RelacionLaboralDto dto = (RelacionLaboralDto) target;

		if(dto!=null && StringUtils.isEmpty(dto.getFechaInicio())) {
			errors.rejectValue("fechaInicio", "relacionLaboral.fechaInicio", mensajes.getString("ERR015"));
		}
		
		if(dto!=null && StringUtils.isEmpty(dto.getFechaFin())) {
			errors.rejectValue("fechaFin", "relacionLaboral.fechaFin",mensajes.getString("ERR016"));
		}
		
		// Verificacion de fechas
		if (dto != null && StringUtils.isNotEmpty(dto.getFechaInicio()) && StringUtils.isNotEmpty(dto.getFechaFin())) {

			try {
				Date fechaInicio = Utils.sdf.parse(dto.getFechaInicio());
				Date fechaFin = Utils.sdf.parse(dto.getFechaFin());
				
				if (fechaInicio.after(fechaFin)) {
					errors.rejectValue("fechaFin", "relacionLaboral.fechaFin", mensajes.getString("ERR017"));
				}
			} catch (ParseException e) {
				errors.rejectValue("fechaFin", "relacionLaboral.fechaFin",	mensajes.getString("ERR018"));
			}
		}

	}

	public void validatePuestosLaborales(RelacionPuestoEmpleadoDto relacionPuestoEmpleadoDto,
			Errors errors) {
		
		if (relacionPuestoEmpleadoDto != null && !StringUtils.isEmpty(relacionPuestoEmpleadoDto.getFechaInicio())) {
			try {
				Utils.sdf.parse(relacionPuestoEmpleadoDto.getFechaInicio());
			}catch (ParseException e) {
						errors.rejectValue( "fechaInicio", "puesto.fechaInicio", mensajes.getString("ERR005"));
			}
		}
		

		if (relacionPuestoEmpleadoDto != null && StringUtils.isEmpty(relacionPuestoEmpleadoDto.getFechaInicio())) {
				errors.rejectValue( "fechaInicio", "puesto.fechaInicio", mensajes.getString("ERR024"));
		}
		
		
	}
	
	public void validateFechas(RelacionLaboralDto relacionLaboralDto,
			BindingResult errors) {
		Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(relacionLaboralDto.getEmpleadoId());
		if(!StringUtils.isEmpty(relacionLaboralDto.getFechaInicio())){
			try {
				Date fechaInicio = Utils.sdf.parse(relacionLaboralDto.getFechaInicio());
				if(fechaInicio.before(empleado.getFechaIngresoNovatium())){
					errors.rejectValue( "fechaInicio", "sueldo.fecha",mensajes.getString("ERR027"));
				}
			}catch (ParseException e) {
						errors.rejectValue( "fechaInicio", "puesto.fechaInicio", mensajes.getString("ERR005"));
			}
		}
		
	}
}
