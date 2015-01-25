package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;

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
	private static final String FTP_ASIGNATURA_MATERIAL = "#{ftpProperties['asignatura.material']}";

	@Value(FTP_CURSO_ASIGNATURA_ROOT)
	private String cursoAsignaturaRoot;

	@Value(FTP_ASIGNATURA_PROGRAMA)
	private String programa;

	@Value(FTP_ASIGNATURA_MATERIAL)
	private String material;

	@Override
	public String guardarPrograma(Asignatura asignatura, MultipartFile file)
			throws CensException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(asignatura.getCurso().getId());
			sb.append(cursoAsignaturaRoot);
			sb.append("/");
			sb.append(asignatura.getId());
			sb.append(programa);
			sb.append("/");
			sb.append(file.getOriginalFilename());
			String filePath = sb.toString();
			
			uploadFile(file.getInputStream(), filePath);
			return filePath;
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new CensException("Error al intentar obtener datos del archivo");
		}
	}
}
