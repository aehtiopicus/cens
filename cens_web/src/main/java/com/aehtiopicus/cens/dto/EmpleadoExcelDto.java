package com.aehtiopicus.cens.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EmpleadoExcelDto {

	private MultipartFile archivo;
	private List<String> errores;
	private String error;
	
	public MultipartFile getArchivo() {
		return archivo;
	}

	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}


	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
