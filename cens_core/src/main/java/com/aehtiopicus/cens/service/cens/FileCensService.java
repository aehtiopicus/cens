package com.aehtiopicus.cens.service.cens;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.enumeration.cens.FileCensInfoType;
import com.aehtiopicus.cens.enumeration.cens.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

public interface FileCensService {

	public FileCensInfo updateFileInfo(FileCensInfo fileInfo) throws CensException;

	public FileCensInfo createNewFileCensService(MultipartFile file, Long miembroRolId,
			PerfilTrabajadorCensType miembroPerfilType,String path,String newFileName, MaterialDidacticoUbicacionType locationType,FileCensInfoType fciType) throws CensException;

	public void deleteFileCensInfo(FileCensInfo fileInfo);

	public void updatePath(List<String> asignaturaPath, Long oldCursoId);

}
