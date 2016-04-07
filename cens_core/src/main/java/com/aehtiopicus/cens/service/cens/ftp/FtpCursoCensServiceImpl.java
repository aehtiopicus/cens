package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class FtpCursoCensServiceImpl extends AbstractFtpCensService implements  FtpCursoCensService{

	private static final Logger logger = LoggerFactory.getLogger(FtpCursoCensServiceImpl.class);
	
	private static final String FTP_CURSO_ASIGNATURA_ROOT = "#{ftpProperties['curso.asignatura.root']}";
	@Value(FTP_CURSO_ASIGNATURA_ROOT)
	private String cursoAsignaturaRoot ;
	
	@Override
	public void createCursoFolder(Curso curso) throws CensException{
		
		FTPClient ftp = ftpConnect();
		try{
			 FTPFile[] cursoDir = ftp.listDirectories(curso.getId().toString());
			 if(cursoDir == null || cursoDir.length==0){
				 String cursoArchive = curso.getId().toString();
				 ftp.makeDirectory(cursoArchive);
				 ftp.makeDirectory(cursoArchive+cursoAsignaturaRoot);				 
				 disconnect(ftp);
			 }
			
		}catch(IOException e){
			logger.error(e.getMessage(),e);
			throw new CensException("Error al interactura con directorio de curso "+curso.getNombre()+"("+curso.getYearCurso()+")");
		}
	}
}
