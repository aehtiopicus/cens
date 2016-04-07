package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

public interface ComentarioCensService {

	public MiembroCens getMiembroCensByPerfilTypeAndRealId(
			PerfilTrabajadorCensType type, Long id) throws CensException;

	public ComentarioCens saveComentario(ComentarioCens cc, MultipartFile file) throws CensException;

	public List<ComentarioCens> findAllParentcomments(Long tipoId,
			ComentarioType tipoType);

	public List<Long> delete(Long comentarioId) throws CensException;

	public ComentarioCens findById(Long programaId);

	public void getArchivoAdjunto(String string, OutputStream baos) throws CensException;

	public ComentarioCens deleteAttachment(Long comentarioId);

	public List<Long> deleteAllComents(List<Long> comentariosId) throws CensException;

	public List<String> getAllKeys(List<Long> comentarioCensId);

}
