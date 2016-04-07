package com.aehtiopicus.cens.service.cens;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.utils.CensException;

@Service
public class ImageResizeCensServiceImpl implements ImageResizeCensService {

	private static final Logger logger = LoggerFactory.getLogger(ImageResizeCensServiceImpl.class);
	@Override
	public InputStream resizeImage(MultipartFile file) throws CensException{
		try{
		BufferedImage bi = ImageIO.read(file.getInputStream());
		bi = Scalr.resize(bi, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_WIDTH,
	               200, 166, Scalr.OP_ANTIALIAS);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, MediaType.parseMediaType(file.getContentType()).getSubtype(), baos);
		
		return new ByteArrayInputStream(baos.toByteArray());
		}catch(Exception e){
			logger.error("Error reading image",e);
			throw new CensException("Error al transformar la imagen");
		}
		
	}
	
}
