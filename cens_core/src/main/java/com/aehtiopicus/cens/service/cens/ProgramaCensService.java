package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.utils.CensException;

public interface ProgramaCensService {

	public Programa savePrograma( Programa p,MultipartFile file) throws CensException;

	public List<Programa> getProgramasForAsignatura(Long id);

	public Programa findById(Long programaId);

	public void getArchivoAdjunto(String fileLocationPath,OutputStream os) throws CensException;

	public void removePrograma(Long programaId) throws CensException;

	public List<Programa> getProgramas();

}
