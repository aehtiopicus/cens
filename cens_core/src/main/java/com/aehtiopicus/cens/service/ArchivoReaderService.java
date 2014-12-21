package com.aehtiopicus.cens.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ArchivoReaderService {

	List<String> readExcelFile(File arch) throws IOException;

	
	
}
