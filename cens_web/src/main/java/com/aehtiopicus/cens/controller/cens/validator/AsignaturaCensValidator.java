package com.aehtiopicus.cens.controller.cens.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.AsignaturaInscripcion;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class AsignaturaCensValidator {

	public void validate(List<Asignatura> asignaturaList)throws CensException{
		if(CollectionUtils.isEmpty(asignaturaList)){
			throw new CensException("No hay datos de la asignatura");
		}
		Map<String,String> errorMap = new HashMap<String,String>();
		
		for(Asignatura asignatura : asignaturaList){
			if(StringUtils.isEmpty(asignatura.getNombre())){
				errorMap.put("nombre", "Nombre es requerido");
			}
			if(StringUtils.isEmpty(asignatura.getModalidad())){
				errorMap.put("modalidad", "Modalidad es requerido");
			}
			if(asignatura.getCurso()== null || asignatura.getCurso().getId()==null){
				errorMap.put("curso", "Curso es requerido");
			}
			if(asignatura.getProfesor()!=null && asignatura.getProfesor().getId() != null){
				if(asignatura.getProfesorSuplente()!=null && asignatura.getProfesorSuplente().getId()!=null){
					if(asignatura.getProfesor().getId().equals(asignatura.getProfesorSuplente().getId())){
						errorMap.put("profesorSuplente", "El profesor suplente no puede ser titular");
					}
				}
				
			}			
		
		if(!errorMap.isEmpty()){
			throw new CensException("Error al guardar Asignatura",errorMap);
		}
		}
	}

	public void validateInscripcion(AsignaturaInscripcion ai) throws CensException {
		if(ai == null){
			throw new CensException("No hay datos de inscripci&oacute;n disponible");
		}
		if(ai.getAsignatura().getId() == null){
			throw new CensException("No hay datos de asignatura para inscripci&oacute;n disponible");
		}
		if(CollectionUtils.isEmpty(ai.getAlumnos())){
			throw new CensException("No hay datos de alumnos para inscripci&oacute;n disponible");
		}
		
	}
}
