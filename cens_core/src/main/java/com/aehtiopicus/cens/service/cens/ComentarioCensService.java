package com.aehtiopicus.cens.service.cens;

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

}
