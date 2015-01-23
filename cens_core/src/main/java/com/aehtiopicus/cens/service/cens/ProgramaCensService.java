package com.aehtiopicus.cens.service.cens;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.utils.CensException;

public interface ProgramaCensService {

	public Programa savePrograma( Programa p,MultipartFile file) throws CensException;

}
