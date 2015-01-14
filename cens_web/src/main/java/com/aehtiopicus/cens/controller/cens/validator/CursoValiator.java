package com.aehtiopicus.cens.controller.cens.validator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class CursoValiator {

	public void validate(List<Curso> cursoList) throws CensException {
		if (CollectionUtils.isEmpty(cursoList)) {
			throw new CensException("No hay datos de cursos");
		}
		Map<String, String> errorMap = new HashMap<String, String>();

		for (Curso curso : cursoList) {
			if (StringUtils.isEmpty(curso.getNombre())) {
				errorMap.put("nombre", "Nombre es requerido");
			}
			if (curso.getYearCurso() == 0) {
				errorMap.put("yearCurso", "El año es requerido");
			}else if(curso.getYearCurso()> Calendar.getInstance().get(Calendar.YEAR)){
				errorMap.put("yearCurso", "El año m&aacute;ximo es el corriente");
			}else if(curso.getYearCurso()<1988){
				errorMap.put("yearCurso", "El año m&iacute;nimo es 1988");
			}
			

			if (!errorMap.isEmpty()) {
				throw new CensException("Error al crear el miembro del cens",
						errorMap);
			}
		}
	}
}
