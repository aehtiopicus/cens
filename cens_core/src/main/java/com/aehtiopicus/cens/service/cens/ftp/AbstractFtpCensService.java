package com.aehtiopicus.cens.service.cens.ftp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aehtiopicus.cens.utils.CensException;

public abstract class AbstractFtpCensService {

	private static final String FTP_USERNAME = "#{ftpProperties['username']}";
	private static final String FTP_PASSWORD = "#{ftpProperties['password']}";
	private static final String FTP_URL = "#{ftpProperties['url']}";
	private static final String FTP_PORT = "#{ftpProperties['port']}";

	@Value(FTP_USERNAME)
	private String username;
	@Value(FTP_PASSWORD)
	private String password;
	@Value(FTP_URL)
	private String url;
	@Value(FTP_PORT)
	private Integer port;

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractFtpCensService.class);

	public FTPClient ftpConnect() throws CensException {

		FTPClient ftp;
		try {
			ftp = new FTPClient();

			ftp.addProtocolCommandListener(new PrintCommandListener(
					new PrintWriter(System.out)));
			int reply;

			ftp.connect(url, port);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP connection error");
				throw new CensException("Error al conectar al servidor FTP");
			}

			ftp.login(username, password);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
		} catch (Exception e) {
			logger.error("FTP connection error", e);
			throw new CensException("Error al conectarse al servidor FTP");
		}
		return ftp;
	}

	public void disconnect(FTPClient ftp) throws CensException {
		if (ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException f) {
				logger.error("FTP disconnect error", f);
				throw new CensException("Error al desconectar FTP");

			}
		}
	}

	public void uploadFile(InputStream input, String fileName)
			throws CensException {

		FTPClient ftp = ftpConnect();

		try {
			logger.info("guardando archivo......" + fileName);
			ftp.storeFile(fileName, input);
		} catch (Exception e) {
			logger.error("FTP disconnect error", e);
			throw new CensException("Error al intentar guardar archivo");
		} finally {
			if (input != null) {
				try{
					input.close();
				}catch(Exception e){
					logger.error(e.getMessage(),e);
					throw new CensException("Error al procesar archivo");
				}
				disconnect(ftp);
			}

		}
	}

	/**
	 * lectura comun del ftp
	 * 
	 * @param ftp
	 * @param output
	 * @param hostDir
	 * @param host
	 * @param user
	 * @param password
	 * @param port
	 * @param filename
	 * @throws Exception
	 */
	private void readFromFtp(FTPClient ftp, OutputStream output,
			String filePath) throws CensException {

		try {
			ftp.retrieveFile(filePath, output);
			logger.info("retrieve completo " + ftp.getReplyString());
			if (ftp.getReplyString().contains("451")) {
				throw new IOException("erorr de lectura reintentando");
			}
		} catch (IOException e) {
			try {
				logger.info("Error reintentando");
				output.flush();				
				ftp.enterLocalActiveMode();
				ftp.retrieveFile(filePath, output);
			} catch (IOException ee) {
				logger.error("Error al leer archivos", ee);
				throw new CensException("Error al leer archivos");
			}

		}

	}

	public List<String> retrieveFileContent(ByteArrayOutputStream output,
			String filePath) throws CensException {

		FTPClient ftp = ftpConnect();

		try {

			readFromFtp(ftp, output, filePath);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(output.toByteArray())));
			List<String> result = new ArrayList<String>();

			while (br.ready()) {
				result.add(br.readLine());

			}
			return result;
		} catch (Exception e) {
			if (e instanceof CensException) {
				throw new CensException(e);
			} else {
				logger.error("Error al leer archivos", e);
				throw new CensException("Error al leer archivos");
			}
		} finally {
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (Exception e) {
					logger.error("Error al leer archivos", e);
					throw new CensException("Error al leer archivos");
				}
				disconnect(ftp);
			}
		}
	}

	public void retrieveFile(OutputStream output, String filePath) throws CensException {

		FTPClient ftp = ftpConnect();

		try {

			readFromFtp(ftp, output, filePath);

		} finally {
			if (ftp != null && ftp.isConnected()) {
				disconnect(ftp);
			}
		}

	}

	public byte[] retrieveByteFile(ByteArrayOutputStream output,
			String filePath) throws CensException {
		FTPClient ftp = ftpConnect();

		try {

			readFromFtp(ftp, output, filePath);

			return output.toByteArray();

		} finally {
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (Exception e) {
					logger.error("Error al leer archivos", e);
					throw new CensException("Error al leer archivos");
				}
				disconnect(ftp);
			}
		}
	}
	
	public void copyFilesFromDirToDi(FTPClient ftp,String dirFrom, String dirTo)throws CensException{
		try{
			for(FTPFile file : ftp.listFiles(dirFrom)){				
				if(!ftp.rename(dirFrom+"/"+file.getName(), dirTo+"/"+file.getName())){								
					throw new Exception("No se pudo renombrar el archivo");
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CensException("Error al copiar archivos");
		}
	}
	
	public void removeDirectory(FTPClient ftp, String dirName) throws CensException{
		try{
			if(!ftp.removeDirectory(dirName)){
				throw new Exception("Error al eliminar el directorio de ftp");
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CensException("Error al eliminar el directorio");
		}
		
	}

}
