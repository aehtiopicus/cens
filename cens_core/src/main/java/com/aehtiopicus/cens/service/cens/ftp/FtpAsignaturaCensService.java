package com.aehtiopicus.cens.service.cens.ftp;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.utils.CensException;

public interface FtpAsignaturaCensService {

	public void createAsignaturaFolder(Asignatura asignatura) throws CensException;

	public void moveAsignaturaData(Long from, Asignatura target) throws CensException;

}
