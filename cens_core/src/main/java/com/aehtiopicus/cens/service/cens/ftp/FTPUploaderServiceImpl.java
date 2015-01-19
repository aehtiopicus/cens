package com.aehtiopicus.cens.service.cens.ftp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.OutputStream;

@Service
public class FTPUploaderServiceImpl implements FTPUploaderService {

//	private static final Logger logger = LoggerFactory.getLogger(FTPUploaderServiceImpl.class);
////    private static final String LAST_UPDATE_VERSION = "last_update.version";
////	private static final String LAST_UPDATE_VERSION = "cfs_09122013105852.cfs";
//
//    private static final String UTF_8 = "UTF-8";
//
//    private static final String EXCEPTION_IN_CONNECTING_TO_FTP_SERVER = "Exception in connecting to FTP Server";
//
//    @Autowired
//    private DumpService dumpService;
//
//    @Autowired(required = true)
//    private UsuarioService usuarioService;
//
//    @Autowired
//    private CFSUpdateService cfsUpdateService;
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see ar.com.midasconsultores.catalogodefiltros.utils.FTPUploaderServiceI#
//     * uploadFile(java.io.InputStream, java.lang.String, java.lang.String,
//     * java.lang.String, java.lang.String, java.lang.String, int)
//     */
//    @Override
//    public void uploadFile(InputStream input, String fileName, String hostDir,
//            String host, String user, String password, int port)
//            throws Exception {
//
//        FTPClient ftp = null;
//
//        try {
//
//            ftp = new FTPClient();
//
//            ftp.addProtocolCommandListener(new PrintCommandListener(
//                    new PrintWriter(System.out)));
//            int reply;
//
//            ftp.connect(host, port);
//            reply = ftp.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                throw new Exception(EXCEPTION_IN_CONNECTING_TO_FTP_SERVER);
//            }
//
//            ftp.login(user, password);
//            ftp.setFileType(FTP.BINARY_FILE_TYPE);
//            ftp.enterLocalPassiveMode();
//            ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
//            logger.info("guardando archivo......"+fileName);
//            ftp.storeFile(hostDir + fileName, input);
//
//        } finally {
//            if (input != null) {
//                input.close();
//                disconnect(ftp);
//            }
//        }
//    }
//    
//      @Override
//    public void uploadFileOutputStream(String fileName, String hostDir,
//            String host, String user, String password, int port)
//            throws Exception {
//
//        FTPClient ftp = null;
//        ByteArrayOutputStream baos = null;
//        ByteArrayInputStream bais =null;
//        try {
//
//            ftp = new FTPClient();
//
//            ftp.addProtocolCommandListener(new PrintCommandListener(
//                    new PrintWriter(System.out)));
//            int reply;
//
//            ftp.connect(host, port);
//            reply = ftp.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                throw new Exception(EXCEPTION_IN_CONNECTING_TO_FTP_SERVER);
//            }
//
//            ftp.login(user, password);
//            ftp.setFileType(FTP.BINARY_FILE_TYPE);
//            ftp.enterLocalPassiveMode();
//            ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
//            logger.info("guardando archivo......"+fileName);
//            
//            baos = new ByteArrayOutputStream();
//           dumpService.obtenerSqlDump(baos);
//            bais = new ByteArrayInputStream(baos.toByteArray());
//           ftp.storeFile(hostDir + fileName, bais);
//            
//            
//        
//            
//            
//            
//
//        } finally {
//             if (bais != null) {
//                bais.close();
//                baos.flush();
//                baos.close();
//                 disconnect(ftp);
//            
//        }
//    }
//    }
//
//    
//
//    private void disconnect(FTPClient ftp) {
//        if (ftp.isConnected()) {
//            try {
//                ftp.logout();
//                ftp.disconnect();
//            } catch (IOException f) {
//                // do nothing as file is already saved to server
//            }
//        }
//    }
//
//    @Override
//    @Deprecated
//    public long retrieveLastUpdateTimeStamp(ByteArrayOutputStream output,
//            String hostDir, String host, String user,
//            String password, int port, LastUpdatedVersionType type) throws Exception {
//
//        FTPClient ftp = null;
//
//        try {
//
//            ftp = new FTPClient();
//            ftp.addProtocolCommandListener(new PrintCommandListener(
//                    new PrintWriter(System.out)));
//            int reply;
//            ftp.connect(host, port);
//            reply = ftp.getReplyCode();
//
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                throw new Exception(EXCEPTION_IN_CONNECTING_TO_FTP_SERVER);
//            }
//
//            ftp.login(user, password);
//            ftp.setFileType(FTP.BINARY_FILE_TYPE);
//            ftp.enterLocalPassiveMode();
//
//            ftp.retrieveFile(hostDir + type.getLastUpdateName(), output);
//
//            String version = new String(output.toByteArray(), UTF_8);
//
//            long versionTimeStamp = 0;
//
//            try {
//                if (!StringUtils.isEmpty(version)) {
//                    versionTimeStamp = Long.parseLong(version);
//                }
//            } catch (NumberFormatException e) {
//                versionTimeStamp = 0;
//            }
//
//            return versionTimeStamp;
//
//        } catch (Exception e) {
//
//            //e.printStackTrace();
//            throw e;
//        } finally {
//            if (output != null) {
//                output.flush();
//                output.close();
//                disconnect(ftp);
//            }
//        }
//
//    }
//		
//    @Override
//    public List<String> retrieveFileContent(ByteArrayOutputStream output, String hostDir, String host, String user, String password, int port, String filename) throws Exception {
//        FTPClient ftp = new FTPClient();
//
//        try {
//
//            readFromFtp(ftp, output, hostDir, host, user, password, port, filename);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(output.toByteArray())));
//            List<String> result = new ArrayList<String>();
//
//            while (br.ready()) {
//                result.add(br.readLine());
//
//            }
//            return result;
//
//        } finally {
//            if (output != null) {
//                output.flush();
//                output.close();
//                disconnect(ftp);
//            }
//        }
//    }
//	
//	
//	@Override
//	public void uploadFiles(List<File> files,String hostDir,
//			String host, String user, String password, int port)
//			throws Exception {
//		logger.info("Cantidad de archivos a guardar: "+files.size());
//		FTPClient ftp = null;
//		try {
//			ftp = new FTPClient();
//			ftp.addProtocolCommandListener(new PrintCommandListener(
//					new PrintWriter(System.out)));
//			int reply;
//			
//			ftp.connect(host, port);
//			reply = ftp.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				ftp.disconnect();
//				throw new Exception(EXCEPTION_IN_CONNECTING_TO_FTP_SERVER);
//			}
//			ftp.login(user, password);
//			ftp.setFileType(FTP.BINARY_FILE_TYPE);
//			ftp.enterLocalPassiveMode();
//			ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
//			for(File file : files){
//				InputStream  input= new FileInputStream(file);
//				ftp.storeFile(hostDir + file.getName(), input);
//				input.close();
//			}
//		} finally {
//				disconnect(ftp);
//		}
//	}
//
//    /**
//     * lectura comun del ftp
//     *
//     * @param ftp
//     * @param output
//     * @param hostDir
//     * @param host
//     * @param user
//     * @param password
//     * @param port
//     * @param filename
//     * @throws Exception
//     */
//	private void readFromFtp(FTPClient ftp, ByteArrayOutputStream output, String hostDir, String host, String user, String password, int port, String filename) throws Exception {
//
//        ftp.addProtocolCommandListener(new PrintCommandListener(
//                new PrintWriter(System.out)));
//        int reply;
//        ftp.connect(host, port);
//        reply = ftp.getReplyCode();
//
//        if (!FTPReply.isPositiveCompletion(reply)) {
//            ftp.disconnect();
//            throw new Exception(EXCEPTION_IN_CONNECTING_TO_FTP_SERVER);
//        }
//
//        ftp.login(user, password);
//        ftp.setFileType(FTP.BINARY_FILE_TYPE);        
//        ftp.enterLocalPassiveMode();
//       
//        try{
//         ftp.retrieveFile(hostDir + filename, output);
//         logger.info("retrieve completo "+ftp.getReplyString());
//         if(ftp.getReplyString().contains("451")){
//             throw new IOException("erorr de lectura reintentando");
//         }
//        }catch(IOException e){
//            e.printStackTrace();
//            output.flush();
//            output.reset();
//            ftp.enterLocalActiveMode();
//            ftp.retrieveFile(hostDir + filename, output);
//            
//        }
//       
//        
//
//    }
//
//    
//    @Override
//    public void retrieveFile(ByteArrayOutputStream output,
//            String hostDir, String host, String user, String password,
//            int port, String filename,boolean binary) throws Exception {
//
//        FTPClient ftp = new FTPClient();
//
//        try {
//
//            readFromFtp(ftp, output, hostDir, host, user, password, port, filename);
//            
//
//        } finally {
//            if (ftp!=null && ftp.isConnected()) {
//                disconnect(ftp);
//            }
//        }
//
//    }
//  
//    @Override
//    public  void  retrieveFileFromFtpAndSaveLocal( String hostDir, String host, String user, String password,
//            int port, List<String> filenames,String location) throws Exception {
//
//        FTPClient ftp = new FTPClient();
//
//        try {
//        	readFromFtpAndSaveLocal(ftp, hostDir, host, user, password, port, filenames,location);
//        	
//        } finally {
//            
//                disconnect(ftp);
//            
//        }
//
//    }
//    
//    private void readFromFtpAndSaveLocal(FTPClient ftp, String hostDir, String host, String user, String password, int port,  List<String> filenames,String location) throws Exception {
//
//        ftp.addProtocolCommandListener(new PrintCommandListener(
//                new PrintWriter(System.out)));
//   
//        int reply;
//        ftp.connect(host, port);
//        reply = ftp.getReplyCode();
//
//        if (!FTPReply.isPositiveCompletion(reply)) {
//            ftp.disconnect();
//            throw new Exception(EXCEPTION_IN_CONNECTING_TO_FTP_SERVER);
//        }
//
//        ftp.login(user, password);
//        ftp.setFileType(FTP.BINARY_FILE_TYPE);
//        ftp.enterLocalPassiveMode();
//        ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
//        for(String fileName :  filenames){
//    		ByteArrayOutputStream output = new ByteArrayOutputStream();
//    		ftp.retrieveFile(hostDir + fileName, output);
//    	    ftp.setControlKeepAliveTimeout(300); // set timeout to 5 minutes
//        	File file = new File(location+fileName);
//			FileOutputStream fos = new FileOutputStream(file);
//			output.writeTo(fos);
//			fos.flush();
//			fos.close();
//			output.flush();
//			output.close();
//    	}
//       
//
//    }
//
//   
//    @Override
//    public byte[] retrieveByteFile(ByteArrayOutputStream output,
//            String hostDir, String host, String user, String password,
//            int port, String filename)throws Exception {
//          FTPClient ftp = new FTPClient();
//
//        try {
//
//            readFromFtp(ftp, output, hostDir, host, user, password, port, filename);
//          
//            return output.toByteArray();
//            
//
//        } finally {
//            if (output != null) {
//                output.flush();
//                output.close();
//                disconnect(ftp);
//            }
//        }
//    }

}
