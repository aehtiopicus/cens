package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class FtpAsignaturaCensServiceImpl extends AbstractFtpCensService implements FtpAsignaturaCensService{

	private static final Logger logger = LoggerFactory.getLogger(FtpAsignaturaCensServiceImpl.class);
	
	private static final String FTP_CURSO_ASIGNATURA_ROOT = "#{ftpProperties['curso.asignatura.root']}";
	private static final String FTP_ASIGNATURA_PROGRAMA = "#{ftpProperties['asignatura.programa']}";
	private static final String FTP_ASIGNATURA_MATERIAL = "#{ftpProperties['asignatura.material']}";
	
	@Value(FTP_CURSO_ASIGNATURA_ROOT)
	private String cursoAsignaturaRoot ;
	
	@Value(FTP_ASIGNATURA_PROGRAMA)
	private String programa ;
	
	@Value(FTP_ASIGNATURA_MATERIAL)
	private String material ;
	
	@Override
	public void createAsignaturaFolder(Asignatura asignatura)throws CensException{
		
		FTPClient ftp = ftpConnect();
		
		try{
			
			String asignaturaFolder = asignaturaPath(asignatura);
			 FTPFile[] cursoDir = ftp.listDirectories(asignaturaFolder);
			 if(cursoDir == null || cursoDir.length==0){				 
				 ftp.makeDirectory(asignaturaFolder);
				 ftp.makeDirectory(asignaturaFolder+programa);
				 ftp.makeDirectory(asignaturaFolder+material);
				 				 
				 disconnect(ftp);
			 }
			
		}catch(IOException e){
			logger.error(e.getMessage(),e);
			throw new CensException("Error al interactura con directorio de asignaruas "+asignatura.getNombre()+"( curso="+asignatura.getCurso().getNombre()+" "+asignatura.getCurso().getYearCurso()+")");
		}
	}
	
	
	private String asignaturaPath(Asignatura asignatura){
		return asignatura.getCurso().getId().toString()+cursoAsignaturaRoot+"/"+asignatura.getId();
	}
	
	
	private String asignaturaProgramaPath(Asignatura asignatura){
		return asignatura.getCurso().getId().toString()+cursoAsignaturaRoot+"/"+asignatura.getId()+programa+"/";
	}
	
	@Override
	public List<String> asignaturaPaths(Asignatura asignatura){
		List<String> list = new ArrayList<String>();
		list.add(asignaturaProgramaPath(asignatura));
		list.add(asignaturMaterialPath(asignatura));
		return list;
	}
	

	private String asignaturMaterialPath(Asignatura asignatura){
		return asignatura.getCurso().getId().toString()+cursoAsignaturaRoot+"/"+asignatura.getId()+material+"/";
	}

	@Override
	public void moveAsignaturaData(Long from, Asignatura target) throws CensException {
		
		FTPClient ftp = ftpConnect();
		
		String asignaturaFrom = asignaturaPath(target).replaceFirst(target.getCurso().getId().toString(), from.toString());
		createAsignaturaFolder(target);		
		String asignaturaTo = asignaturaPath(target);
		try{
			for(FTPFile file : ftp.listDirectories(asignaturaFrom)){
				copyFilesFromDirToDi(ftp, asignaturaFrom+"/"+file.getName(),asignaturaTo+"/"+file.getName() );
				removeDirectory(ftp,asignaturaFrom+"/"+file.getName());
			}
			removeDirectory(ftp, asignaturaFrom);
		}catch(IOException e){
			logger.error(e.getMessage(),e);
			throw new CensException("Error al listar informaci&oacute;n");
		}
		
		
	}

	
}
