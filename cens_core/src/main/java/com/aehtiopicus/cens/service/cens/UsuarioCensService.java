package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.utils.CensException;

public interface UsuarioCensService {

	public Usuarios saveUsuario(Usuarios usuarios) throws CensException;

	public void deleteUsuarioByMiembroId(Long miembroId);

	public Usuarios findUsuarioByUsername(String username);

	public Usuarios findUsuarioById(Long id);

	public void resetPassword(Long usuarioId, String defaulPassword);

	public void updateImage(Usuarios user, MultipartFile file) throws CensException;

	public void getAvatar(String picturePath, OutputStream baos) throws Exception;

	public List<Object[]> getUsuarioActivoByUserName();

	public List<Object[]> getUsuarioAsesorActivoByUserName();
}
