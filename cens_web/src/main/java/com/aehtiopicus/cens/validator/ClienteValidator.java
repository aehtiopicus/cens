package com.aehtiopicus.cens.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.dto.ClienteDto;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.util.Utils;

@Service
public class ClienteValidator implements Validator{
	
	@Autowired
	private ClienteService clienteService;
	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	private SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
	

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ClienteDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ClienteDto clienteDto = (ClienteDto) target;
		
		Cliente cliente = clienteService.getClienteByNombre(clienteDto.getNombre());
		
		if (cliente != null) {
			if(clienteDto.getClienteId() == null || clienteDto.getClienteId() < 1){
				errors.rejectValue( "razonSocial", "razonSocial.duplicado", mensajes.getString("ERR011"));
			}else if(!clienteDto.getClienteId().equals(cliente.getId())){
				errors.rejectValue( "razonSocial", "razonSocial.duplicado",mensajes.getString("ERR011"));				
			}
		}
		
		// La fecha de alta del cliente no puede ser menor a la fecha de baja.
		if (clienteDto != null && StringUtils.isNotEmpty(clienteDto.getFecha_baja()) && StringUtils.isNotEmpty(clienteDto.getFecha_alta())) {
			try {
				Date fechaEgreso = sdf.parse(clienteDto.getFecha_baja());
				Date fechaIngreso = sdf.parse(clienteDto.getFecha_alta());
				if(fechaEgreso.before(fechaIngreso)){
					errors.rejectValue( "fecha_baja", "cliente.fecha_baja", mensajes.getString("ERR012"));
				}
			} catch (ParseException e) {
				errors.rejectValue( "fecha_baja", "empleado.cliente.fecha_baja",mensajes.getString("ERR005"));
			}
		}
		
		
		// La fecha de alta del cliente debe tener el formato correcto
		if (clienteDto != null && StringUtils.isNotEmpty(clienteDto.getFecha_alta())) {
			try {
				sdf.parse(clienteDto.getFecha_alta());
			}catch (ParseException e) {
						errors.rejectValue( "fecha_alta", "cliente.fecha_alta",mensajes.getString("ERR005"));
			}
		}
		
		// La fecha de baja del cliente debe tener el formato correcto
		if (clienteDto != null && StringUtils.isNotEmpty(clienteDto.getFecha_baja())) {
			try {
				sdf.parse(clienteDto.getFecha_baja());
			}catch (ParseException e) {
						errors.rejectValue( "fecha_baja", "cliente.fecha_baja", mensajes.getString("ERR005"));
			}
		}
		
		//Números de telefono deben ser numericos
		if (clienteDto != null && StringUtils.isNotEmpty(clienteDto.getTelefono())) {
				try {
					Long.parseLong((clienteDto.getTelefono()));
				}catch (NumberFormatException e) {
					errors.rejectValue( "telefono", "cliente.telefono",mensajes.getString("ERR006"));
				}
		}
		
		//Gerente de operación
		if (clienteDto != null && (clienteDto.getUsuarioGerenteOperadorId() ==null ||clienteDto.getUsuarioGerenteOperadorId()<1)) {
			
			errors.rejectValue( "usuarioGerenteOperadorId", "cliente.gerenteOperacion", mensajes.getString("ERR013"));

		}
	}

	

}
