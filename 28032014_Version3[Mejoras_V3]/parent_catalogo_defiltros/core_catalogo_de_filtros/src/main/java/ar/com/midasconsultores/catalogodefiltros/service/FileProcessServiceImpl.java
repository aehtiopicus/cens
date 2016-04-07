package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ar.com.midasconsultores.catalogodefiltros.utils.FTPUploaderService;

@Service
public class FileProcessServiceImpl implements FileProcessService{
	
	private static final Logger logger = LoggerFactory.getLogger(FileProcessServiceImpl.class);
	
	@Autowired
	private FTPUploaderService ftpUploaderService;
	
	/**
	 * En base a los archivos existentes se crea un archivo txt con 
	 * el nombre y el codigo de verificacion
	 * 
	 */
	public void createCodificationFile(String[] nameOfFiles, String location) throws Exception {
		  logger.info("creando archivo de indice");
		  HashMap<String,Long> crcCode = new HashMap<String,Long>();
		 
		  if(nameOfFiles != null){
			
			  for(int i=0;i< nameOfFiles.length; i++){
		    	  File file = new File(location+nameOfFiles[i]);
		    	  if(!(file.getAbsolutePath().contains(".txt") || file.getAbsolutePath().contains(".pdf"))){
		    		  crcCode.put(file.getName(),calculateCRCCode(file.getAbsolutePath()));  
		    	  }
		      }  
		  
		  }
		
		
		  codificarArchivo(crcCode,location);
	    }
		

	/**
	 * Crea el archivo con el nombre del archivo y el codigo CRC
	 * @param crcCode
	 * @param location
	 */
	private void codificarArchivo(HashMap<String, Long> crcCode,String location) {
			
		File archivoCodificado = new File(location+"indice.txt");
			try{
				int cont = 0;
				FileWriter w = new FileWriter(archivoCodificado);
				BufferedWriter bw = new BufferedWriter(w);
				PrintWriter wr = new PrintWriter(bw);
				Set<String> keys = crcCode.keySet();
				for(String key : keys){
					// name of file | codigo crc
					wr.append(key.toString()+","+crcCode.get(key)+"\r\n");
					cont++;
				}
				wr.close();
				bw.close();
				logger.info("cantidad de registros escritos: "+cont);
			}catch(Exception e){
				logger.error("ocurrio un error al crear el archivo de codificacion");
			}
		}

	
		/**
		 * crea para cada archivo un codigo CRC
		 * @param filepath
		 * @return
		 * @throws Exception
		 */
		private long calculateCRCCode(String filepath) throws Exception{
			InputStream inputStream = new BufferedInputStream(new FileInputStream(filepath));
			CRC32 crc = new CRC32();
		    int cnt;
		    while ((cnt = inputStream.read()) != -1) {
		 		 crc.update(cnt);
		    }
		    inputStream.close();
		   	return crc.getValue();
		}


		/**
		 * devuelve una lista con los nombres de archivos que deben actualizarse o crearse
		 */
		@Override
		public List<String> getNameOfFilesToProcess(List<String> contentIndex,List<String> contentIndex2) {
			logger.info("cargando nombre de archivos a actualizar");
			List<String> nameOfFiles = new ArrayList<String>();
			if(!CollectionUtils.isEmpty(contentIndex)){
				HashMap<String,Long> fotosMap = crearMapa(contentIndex);
				for(String line : contentIndex2){
					String[] templine = line.split(",");
					//si no existe el archivo se agrega
					if(!fotosMap.containsKey(templine[0])){
						nameOfFiles.add(templine[0]);
					}else{
						// si contiene el archivo se debe verificar si el codigo ha sido actualizado
						if(!fotosMap.get(templine[0]).equals(Long.valueOf(templine[1]))){
							nameOfFiles.add(templine[0]);
						}
					}
					
				}
			}else{
				if(!CollectionUtils.isEmpty(contentIndex2)){
					for(String line : contentIndex2){
						String[] templine = line.split(",");
						nameOfFiles.add(templine[0]);
					}
				}
			}
			logger.info("cantidad de archivos a guardar: "+nameOfFiles.size());
			return nameOfFiles;
		}


	


		/**
		 * crear un mapa con el nombre del archivo como key
		 * @param ftpContentIndex
		 * @return
		 */
		private HashMap<String, Long> crearMapa(List<String> ftpContentIndex) {
			logger.info("creando mapa para indexar");
			HashMap<String, Long> map = new HashMap<String, Long>();
			if(!CollectionUtils.isEmpty(ftpContentIndex)){
				for(String content : ftpContentIndex){
					String[] templine = content.split(",");	
					map.put(templine[0],Long.valueOf(templine[1]));
				}
				
			}
			return map;
		}
	    	 
		/**
		 * Se recupera el archivo de indice local para ser comparado con el archivo de
		 * indice del ftp
		 * @return
		 * @throws IOException
		 */
		@Override
		public List<String> getContentLocalIndexFile(String url) throws IOException {
			File in = new File(url);
			List<String> localindex = FileUtils.readLines(in, "UTF-8");
		    return localindex;
		}


		/**
		 * Se obtiene el archivo de indices del ftp
		 */
		@Override
		public List<String> getContentFtpIndexFile(String hostFolder,String host, String user,String password, String port,String diferencias) throws  Exception {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			return ftpUploaderService.retrieveFileContent(output,hostFolder, host,user, password, Integer.parseInt(port),diferencias);
		}


		

}
