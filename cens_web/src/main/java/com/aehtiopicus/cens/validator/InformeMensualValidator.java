package com.aehtiopicus.cens.validator;

import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.dto.InformeMensualHeaderNuevoDto;
import com.aehtiopicus.cens.service.InformeMensualService;
import com.aehtiopicus.cens.utils.PeriodoUtils;

@Service
public class InformeMensualValidator implements Validator {

	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	@Autowired
	private InformeMensualService informeMensualService;
	public void setInformeMensualService(InformeMensualService informeMensualService) {
		this.informeMensualService = informeMensualService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return InformeMensualHeaderNuevoDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InformeMensualHeaderNuevoDto informe = (InformeMensualHeaderNuevoDto) target;

		Cliente c = new Cliente();
		c.setId(informe.getClienteId());
		Date p = PeriodoUtils.getDateFormPeriodo(informe.getPeriodo());
		
		InformeMensual im = informeMensualService.getByClienteAndPeriodo(c, p);
		if(im != null) {
			errors.rejectValue( "periodo", "periodo.informecreado",  mensajes.getString("ERR014"));
		}
	}

}
