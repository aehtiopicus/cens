package com.aehtiopicus.cens.service.cens.ftp;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
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

	
	private static final Logger logger = LoggerFactory.getLogger(AbstractFtpCensService.class);
	
	public FTPClient ftpConnect()throws CensException{
		
		FTPClient ftp;
		try{
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
		}catch(Exception e){
			  logger.error("FTP connection error",e);
			  throw new CensException("Error al conectarse al servidor FTP");
		}
		return ftp;
	}
    public void disconnect(FTPClient ftp) throws CensException{
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException f) {
            	logger.error("FTP disconnect error",f);
                throw new CensException("Error al desconectar FTP");
                
            }
        }
    }
}
