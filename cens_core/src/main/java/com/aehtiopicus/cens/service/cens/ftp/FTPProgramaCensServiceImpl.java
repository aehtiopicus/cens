package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class FTPProgramaCensServiceImpl extends AbstractFtpCensService
		implements FTPProgramaCensService {

	private static final Logger logger = LoggerFactory
			.getLogger(FTPProgramaCensServiceImpl.class);

	private static final String FTP_CURSO_ASIGNATURA_ROOT = "#{ftpProperties['curso.asignatura.root']}";
	private static final String FTP_ASIGNATURA_PROGRAMA = "#{ftpProperties['asignatura.programa']}";


	@Value(FTP_CURSO_ASIGNATURA_ROOT)
	private String cursoAsignaturaRoot;

	@Value(FTP_ASIGNATURA_PROGRAMA)
	private String programa;



	@Override
	public void guardarPrograma( MultipartFile file,String filePath)
			throws CensException {
		try {			
			uploadFile(file.getInputStream(), filePath);			
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new CensException("Error al intentar obtener datos del archivo");
		}
	}
	
	@Override
	public String getRutaPrograma(Asignatura asignatura){
		StringBuilder sb = new StringBuilder();
		sb.append(asignatura.getCurso().getId());
		sb.append(cursoAsignaturaRoot);
		sb.append("/");
		sb.append(asignatura.getId());
		sb.append(programa+"/");		
		return sb.toString();
	}

	@Override
	public void leerPrograma(String fileLocationPath, OutputStream os) throws CensException{
		this.retrieveFile(os, fileLocationPath);
		
	}
}
