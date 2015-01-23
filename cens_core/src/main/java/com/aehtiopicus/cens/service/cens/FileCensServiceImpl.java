package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.repository.cens.FileCensRepository;

@Service
public class FileCensServiceImpl implements FileCensService{

	@Autowired
	private FileCensRepository fileCensRepository;
	
	@Override
	public FileCensInfo copyData(FileCensInfo fciNew, FileCensInfo fciOld){
		if(fciNew==null && fciOld == null){
			return null;
		}
		if(fciNew!=null && fciOld==null){
			return fciNew;
		}
		if(fciNew==null && fciOld!=null){
			return fciOld;
		}
		if(fciNew.getFileName().equals(fciOld.getFileName())){			
			fciOld.setUpdaterId(fciNew.getCreatorId());
			fciOld.setUpdateDate(fciNew.getFileLastModify());
			fciOld.setUpdaterType(fciNew.getUpdaterType());
			return fciOld;
		}else{
			return fciNew;
		}
	}

	@Override
	public FileCensInfo updateFileInfo(FileCensInfo fileInfo) {
		return fileCensRepository.save(fileInfo);
		
	}
}
