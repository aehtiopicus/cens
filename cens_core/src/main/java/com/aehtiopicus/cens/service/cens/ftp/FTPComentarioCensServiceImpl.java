package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.utils.CensException;

@Service
public class FTPComentarioCensServiceImpl extends AbstractFtpCensService implements FTPComentarioCensService {

	private static final Logger logger = LoggerFactory
			.getLogger(FTPProgramaCensServiceImpl.class);

	private static final String FTP_COMENTARIO_ROOT = "#{ftpProperties['comentario']}";	


	@Value(FTP_COMENTARIO_ROOT)
	private String comentarioRoot;


	@Override
	public String getRutaComentario() {
		return comentarioRoot+"/";
	}


	@Override
	public void guardarPrograma(MultipartFile file, String filePath) throws CensException {
		try {			
			createComentarioFolder();
			uploadFile(file.getInputStream(), filePath);			
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new CensException("Error al intentar obtener datos del archivo");
		}
	}
	
	private void createComentarioFolder()throws CensException{
		
		FTPClient ftp = ftpConnect();
		
		try{
			
			String comentarioFolder = getRutaComentario();
			 FTPFile[] comentarioDir = ftp.listDirectories(comentarioFolder);
			 if(comentarioDir == null || comentarioDir.length==0){				 
				 ftp.makeDirectory(comentarioFolder);				 				 
				 disconnect(ftp);
			 }
			
		}catch(IOException e){
			logger.error(e.getMessage(),e);
			throw new CensException("Error al interactura con directorio de comentarios ");
		}
	}


	@Override
	public void leerComentario(String fileLocationPath, OutputStream os)throws CensException {
		this.retrieveFile(os, fileLocationPath);
		
	}


}
