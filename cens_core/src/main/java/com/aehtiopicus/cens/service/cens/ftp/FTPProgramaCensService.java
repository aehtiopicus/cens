package com.aehtiopicus.cens.service.cens.ftp;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.utils.CensException;

public interface FTPProgramaCensService {

	public void guardarPrograma(Asignatura asignatura, MultipartFile file,String filePath)
			throws CensException;

	public String getRutaPrograma(Asignatura asignatura, MultipartFile file);

	public void leerPrograma(String fileLocationPath, OutputStream os) throws CensException;

}
