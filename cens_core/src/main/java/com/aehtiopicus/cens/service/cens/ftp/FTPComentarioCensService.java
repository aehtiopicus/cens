package com.aehtiopicus.cens.service.cens.ftp;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.utils.CensException;

public interface FTPComentarioCensService {

	public String getRutaComentario();

	public void guardarPrograma(MultipartFile file, String filePath) throws CensException;

	public void leerComentario(String fileLocationPath, OutputStream os) throws CensException;

}
