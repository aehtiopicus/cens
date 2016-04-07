package com.aehtiopicus.cens.service.cens;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.enumeration.cens.FileCensInfoType;
import com.aehtiopicus.cens.enumeration.cens.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.FileCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class FileCensServiceImpl implements FileCensService{

	@Autowired
	private FileCensRepository fileCensRepository;
		

	@Override
	public FileCensInfo updateFileInfo(FileCensInfo fileInfo) {
		return fileCensRepository.save(fileInfo);
		
	}

	@Override
	@Transactional
	public FileCensInfo createNewFileCensService(MultipartFile file,
			Long miembroRolId, PerfilTrabajadorCensType miembroPerfilType,String path,String newFileName, MaterialDidacticoUbicacionType locationType,FileCensInfoType fciType)
			throws CensException {
		FileCensInfo fci = new FileCensInfo();
		fci.setCreationDate(new Date());
		fci.setCreatorId(miembroRolId);
		fci.setCreatorType(miembroPerfilType);
		fci.setFileName(file.getOriginalFilename());
		fci.setFileSize(file.getSize());
		fci.setFileLocation(locationType);
		fci.setFileLocationPath(path);
		fci.setFileType(fciType);
		fci.setRealFileName(newFileName);
		return fileCensRepository.save(fci);
	}

	@Override
	@Transactional
	public void deleteFileCensInfo(FileCensInfo fileInfo) {
		fileCensRepository.softDelete(fileInfo);
		
	}

	@Override
	@Transactional
	public void updatePath(List<String> asignaturaPath, Long oldCursoId) {
		String oldPath=null;
		for(String pathToChange : asignaturaPath){
			oldPath =oldCursoId+pathToChange.substring(pathToChange.indexOf("/"), pathToChange.length());
			fileCensRepository.updateFileInfo(oldPath,pathToChange);
		}		
		
	}
	
	
}
