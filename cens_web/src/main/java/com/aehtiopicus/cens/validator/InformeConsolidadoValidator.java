package com.aehtiopicus.cens.validator;

import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.dto.InformeConsolidadoHeaderDto;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;
import com.aehtiopicus.cens.service.InformeConsolidadoService;
import com.aehtiopicus.cens.utils.PeriodoUtils;

@Service
public class InformeConsolidadoValidator implements Validator {

	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	@Autowired
	private InformeConsolidadoService informeConsolidadoService;
	public void setInformeConsolidadoService(InformeConsolidadoService informeConsolidadoService) {
		this.informeConsolidadoService = informeConsolidadoService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return InformeConsolidadoHeaderDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InformeConsolidadoHeaderDto informe = (InformeConsolidadoHeaderDto) target;


		Date p = PeriodoUtils.getDateFormPeriodo(informe.getPeriodo());
		
		InformeConsolidado ic = informeConsolidadoService.getByPeriodoAndTipo(p, InformeConsolidadoTipoEnum.COMUN);
		if(ic != null) {
			errors.rejectValue( "periodo", "periodo.informecreado",  mensajes.getString("ERR014"));
		}
	}

}
