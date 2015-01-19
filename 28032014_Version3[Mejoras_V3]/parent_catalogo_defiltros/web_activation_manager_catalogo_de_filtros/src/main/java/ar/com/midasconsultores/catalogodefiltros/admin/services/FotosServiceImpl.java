package ar.com.midasconsultores.catalogodefiltros.admin.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ar.com.midasconsultores.catalogodefiltros.service.FileProcessService;
import ar.com.midasconsultores.catalogodefiltros.service.UsuarioService;
import ar.com.midasconsultores.catalogodefiltros.utils.FTPUploaderService;

@Service
public class FotosServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(FotosServiceImpl.class);
	private static final String FTP_PROPERTIES_HOST = "#{ftpProperties['host']}";
	private static final String FTP_PROPERTIES_USER = "#{ftpProperties['user']}";
	private static final String FTP_PROPERTIES_PASSWORD = "#{ftpProperties['password']}";
	private static final String FTP_PROPERTIES_PORT = "#{ftpProperties['port']}";
	private static final String FTP_PROPERTIES_HOST_FOLDER = "#{ftpProperties['hostImageFolder']}";
	private static final String FOTOS_PROPERTIES_URL = "#{configProperties['external_file_path']}";
	private static final String FILE_PROPERTIES_NAME = "#{configProperties['file_index_name']}";
    private static final String LAST_UPDATE_FOTOS = "#{configProperties['file_fotos_name']}";
    private static final String FTP_HOST_FOLDER = "#{ftpProperties['hostFolder']}";
    
	@Value(FTP_PROPERTIES_HOST)
	private String host;
	
	@Value(FTP_HOST_FOLDER)
	private String hostFolder;

	@Value(FTP_PROPERTIES_USER)
	private String user;

	@Value(FTP_PROPERTIES_PASSWORD)
	private String password;

	@Value(FTP_PROPERTIES_PORT)
	private String port;

	@Value(FTP_PROPERTIES_HOST_FOLDER)
	private String hostFolderImage;
	
	@Value(FILE_PROPERTIES_NAME)
	private String diferencias;
	
	@Value(FOTOS_PROPERTIES_URL)
	private String fotosUrl;
	
	@Autowired
	private FTPUploaderService ftpUploaderService;
	
	@Autowired(required = true)
	private UsuarioService usuarioService;
	
	@Autowired(required = true)
	private FileProcessService fileCodificationService;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Value(LAST_UPDATE_FOTOS)
	private String lastUpdateFotos;

	
	public void cargarFotos() throws Exception {
		logger.info("leyendo directorio..");
		File ds = new File(fotosUrl);
		fileCodificationService.createCodificationFile(ds.list(), fotosUrl);
		List<String> ftpContentIndex = fileCodificationService.getContentFtpIndexFile(hostFolder,host, user, password,port,diferencias);
		List<String> localContentIndex = fileCodificationService.getContentLocalIndexFile(fotosUrl+diferencias);
		List<String> filesToUpload = fileCodificationService.getNameOfFilesToProcess(ftpContentIndex,localContentIndex);
		List<File> files = getFilesToSave(filesToUpload);
		saveFotos(files);
		actualizarIndiceEnFtp();
	}

	/**
	 * El indice creado se guarda en el ftp
	 */
	private void actualizarIndiceEnFtp() throws  Exception {
		File archivoCodificado = new File(fotosUrl+diferencias);
		InputStream in = new FileInputStream(archivoCodificado);
		ftpUploaderService.uploadFile(in, diferencias, hostFolder, host, user, password, Integer.parseInt(port));
		
	}

	/**
	 * Se guardan en el ftp solo los archivos nuevos o actualizados
	 * @param inputs
	 * @throws Exception
	 */
	private void saveFotos(List<File> inputs) throws Exception {
		
		if(!CollectionUtils.isEmpty(inputs)){
			ftpUploaderService.uploadFiles(inputs, hostFolderImage, host,user, password, Integer.parseInt(port));
			cargarActualizacion();
		}
		
	}

	private void cargarActualizacion() throws IOException {
		
		File fechaActualizacion = new File(fotosUrl+lastUpdateFotos);
		try{
			Calendar currentDate = Calendar.getInstance();
			Long currentDateInMilliseconds = currentDate.getTimeInMillis();
			FileWriter w = new FileWriter(fechaActualizacion);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			wr.append(currentDateInMilliseconds.toString());
			wr.close();
			bw.close();
			InputStream in = new FileInputStream(fechaActualizacion);
			ftpUploaderService.uploadFile(in, lastUpdateFotos, hostFolder, host, user, password, Integer.parseInt(port));
			fechaActualizacion.delete();
		}catch(Exception e){
			logger.error("ocurrio un error al crear el archivo de codificacion");
		}
		
	}

	/**
	 * se obtienen a partir del nombre los archivos a ser guardados
	 * 
	 */
	private List<File> getFilesToSave(List<String> filesToUpload) throws FileNotFoundException {
		List<File> inputs = new ArrayList<File>();
		if(!CollectionUtils.isEmpty(filesToUpload)){
			for(String name : filesToUpload){
				File file = new File(fotosUrl+name);
				inputs.add(file);
			}	
		}
		return inputs;
	}

	

	

}
