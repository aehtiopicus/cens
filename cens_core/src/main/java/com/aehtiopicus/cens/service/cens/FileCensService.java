package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.utils.CensException;

public interface FileCensService {

	public FileCensInfo copyData(FileCensInfo fciNew, FileCensInfo fciOld);

	public FileCensInfo updateFileInfo(FileCensInfo fileInfo) throws CensException;

}
