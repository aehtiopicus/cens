package com.aehtiopicus.cens.validator;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



import com.aehtiopicus.cens.dto.EmpleadoExcelDto;

@Service
public class EmpleadoExcelValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return EmpleadoExcelDto.class.equals(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		EmpleadoExcelDto bean = (EmpleadoExcelDto) target;
		bean.setError(null);
		
		if (bean.getArchivo() == null || bean.getArchivo().getSize() == 0 ) {
			errors.rejectValue("archivo","error.no-especificado", null,
					"Valor requerido.");
			return;
		}
			
			String nombre = bean.getArchivo().getOriginalFilename();
			String extencion =getFileExtension(nombre);
			
			if(!extencion.equals("xls")  && !extencion.equals("xlsx")) {
				errors.rejectValue("archivo","error.no-especificado", null,
						"Formato de archivo incorrecto");
				return;
			}
		
	}
	 private static String getFileExtension(String fname2)
	  {
	      String fileName = fname2;
	      String ext="";
	      int mid= fileName.lastIndexOf(".");
	      ext=fileName.substring(mid+1,fileName.length());
	      return ext;
	  }
}
