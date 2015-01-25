package com.aehtiopicus.cens.controller.cens.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.utils.CensException;

@Component
public class FileUploadCensValidator {

	private static final long MAX_FILE_SIZE = 10485760; //10MB
	private static final long MIN_FILE_SIZE = 0;
	
	private static final String FILE_EXTENSION_VALIDATION_PATTERN = "([^\\s]+(\\.(?i)(xlsx|xls|doc|docx|ppt|pptx|pps|ppsx|pdf))$)";
	
	public void validate(MultipartFile file) throws CensException{				
			
		if(StringUtils.isEmpty(file.getName())){
			throw new CensException("Error al adjuntar Archivo","fileupload", "Nombre de archivo requerido");
		}
		if(file.getSize()==MIN_FILE_SIZE){
			throw new CensException("Error al adjuntar Archivo","fileupload", "El archivo no contiene informaci&oacute;n");
		}
		if(file.getSize()==MAX_FILE_SIZE){
			throw new CensException("Error al adjuntar Archivo","fileupload", "El archivo debe ser menor de 10 mb");
		}
		
        if(! Pattern.compile(FILE_EXTENSION_VALIDATION_PATTERN).matcher(
                        file.getOriginalFilename()).matches()){
        	throw new CensException("Error al adjuntar Archivo","fileupload", "Formato v&aacute;lido 'xlsx','xls','doc','docx','ppt','pptx','pps','ppsx','pdf'");
        }		
		
		
	}
}
