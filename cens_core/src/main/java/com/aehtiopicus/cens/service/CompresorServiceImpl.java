package com.aehtiopicus.cens.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;
@Service
public class CompresorServiceImpl implements CompresorService {

	@Override
	public ByteArrayOutputStream comprimirPagos(List<String> filenames) throws IOException {
		byte[] buf = new byte[2048];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(baos);
		for (int i=0; i<filenames.size(); i++) {
					FileInputStream fis = new FileInputStream(filenames.get(i).toString());
				    BufferedInputStream bis = new BufferedInputStream(fis);
				 // agregar a zip el archivo creado
				    File file = new File(filenames.get(i).toString());
				    String entryname = file.getName();
				    out.putNextEntry(new ZipEntry(entryname));
				    int bytesRead;
				    while ((bytesRead = bis.read(buf)) != -1) {
				    out.write(buf, 0, bytesRead);
				    }
				    out.closeEntry();
				    bis.close();
				    fis.close();
		    }
		    out.flush();
		    baos.flush();
		    out.close();
		    baos.close();
		return baos;
	}

}
