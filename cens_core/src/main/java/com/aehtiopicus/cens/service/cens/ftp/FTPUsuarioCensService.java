package com.aehtiopicus.cens.service.cens.ftp;

import java.io.InputStream;
import java.io.OutputStream;

import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.utils.CensException;

public interface FTPUsuarioCensService {

	public void createAvatarFolder(Usuarios usuario) throws CensException;

	public String avatarPath(Usuarios usuario);

	public void guardarImagenUsuario(InputStream is, String filePath)
			throws CensException;

	public void leerAvatar(String picturePath, OutputStream baos) throws CensException;

}
