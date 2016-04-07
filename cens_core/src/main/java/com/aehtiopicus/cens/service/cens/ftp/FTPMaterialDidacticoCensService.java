package com.aehtiopicus.cens.service.cens.ftp;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.utils.CensException;

public interface FTPMaterialDidacticoCensService {

	public void leerMaterialDidactico(String fileLocationPath, OutputStream os)
			throws CensException;

	public String getRutaMaterialDidactico(Asignatura asignatura);

	public void guardarMaterialDidactico(MultipartFile file, String filePath)
			throws CensException;

}
