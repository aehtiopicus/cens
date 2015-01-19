package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class FtpAsignaturaCensServiceImpl extends AbstractFtpCensService implements FtpAsignaturaCensService{

	private static final Logger logger = LoggerFactory.getLogger(FtpAsignaturaCensServiceImpl.class);
	
	@Override
	public void createAsignaturaFolder(Asignatura asignatura)throws CensException{
		
		FTPClient ftp = ftpConnect();
		try{
			String cursoFolder = asignatura.getCurso().getId().toString();
			 FTPFile[] cursoDir = ftp.listDirectories(cursoFolder+FtpCursoCensServiceImpl.CURSO_FTP_ASIGNATURAS+"/"+asignatura.getId());
			 if(cursoDir == null || cursoDir.length==0){
				 String asignaturaArchive = asignatura.getId().toString();
				 ftp.makeDirectory(cursoFolder+FtpCursoCensServiceImpl.CURSO_FTP_ASIGNATURAS+"/"+asignaturaArchive);
				 				 
				 disconnect(ftp);
			 }
			
		}catch(IOException e){
			logger.error(e.getMessage(),e);
			throw new CensException("Error al interactura con directorio de asignaruas "+asignatura.getNombre()+"( curso="+asignatura.getCurso().getNombre()+" "+asignatura.getCurso().getYearCurso()+")");
		}
	}
}
