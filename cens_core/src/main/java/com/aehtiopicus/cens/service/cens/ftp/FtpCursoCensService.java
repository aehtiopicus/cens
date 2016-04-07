package com.aehtiopicus.cens.service.cens.ftp;

import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.utils.CensException;

public interface FtpCursoCensService {

	public void createCursoFolder(Curso curso) throws CensException;

}
