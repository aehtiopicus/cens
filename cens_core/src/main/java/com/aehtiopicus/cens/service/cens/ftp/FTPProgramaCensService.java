package com.aehtiopicus.cens.service.cens.ftp;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.utils.CensException;

public interface FTPProgramaCensService {

	public String guardarPrograma(Asignatura asignatura, MultipartFile file)
			throws CensException;

}
