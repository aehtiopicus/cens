package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class FTPUsuarioCensServiceImpl extends AbstractFtpCensService implements
		FTPUsuarioCensService {

	private static final Logger logger = LoggerFactory
			.getLogger(FtpAsignaturaCensServiceImpl.class);

	private static final String FTP_AVATAR = "#{ftpProperties['avatar']}";

	@Value(FTP_AVATAR)
	private String avatar;

	@Override
	public void createAvatarFolder(Usuarios usuario) throws CensException {

		FTPClient ftp = ftpConnect();

		try {
			String usuarioFolder = avatar + "/" + usuario.getId();
			FTPFile[] cursoDir = ftp.listDirectories(usuarioFolder);
			if (cursoDir == null || cursoDir.length == 0) {
				ftp.makeDirectory(usuarioFolder);
				disconnect(ftp);
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CensException(
					"Error al interactura con directorio de usuario ");
		}
	}

	@Override
	public String avatarPath(Usuarios usuario) {
		try {
			createAvatarFolder(usuario);
		} catch (Exception e) {
		}
		
		return avatar + "/" + usuario.getId()+ "/";
	}

	@Override
	public void guardarImagenUsuario(InputStream is, String filePath)
			throws CensException {
		
			uploadFile(is, filePath);		
	}

	@Override
	public void leerAvatar(String picturePath, OutputStream baos) throws CensException{
		this.retrieveFile(baos, picturePath);
		
	}

}
