package ar.com.midasconsultores.catalogodefiltros.controller;

import org.springframework.web.multipart.MultipartFile;

import ar.com.midasconsultores.catalogodefiltros.dto.ActivationDataDTO;

public interface ActivationService {
	
	public ActivationDataDTO obtenerInformacionActivacion();
	
	public boolean comprobarActivacion();
	
	public boolean validarCodigos(MultipartFile mf);
	
//	public boolean dumpCodigos(MultipartFile mf);
}
