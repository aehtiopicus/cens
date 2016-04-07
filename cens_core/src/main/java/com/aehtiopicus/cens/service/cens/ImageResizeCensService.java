package com.aehtiopicus.cens.service.cens;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.utils.CensException;

public interface ImageResizeCensService {

	public InputStream resizeImage(MultipartFile file) throws CensException;

}
