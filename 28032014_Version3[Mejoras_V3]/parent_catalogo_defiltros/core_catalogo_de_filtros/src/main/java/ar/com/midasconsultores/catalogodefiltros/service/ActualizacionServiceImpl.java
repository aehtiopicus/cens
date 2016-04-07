/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.utils.FTPUploaderService;
import ar.com.midasconsultores.catalogodefiltros.utils.LastUpdatedVersionType;
import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;
import ar.com.midasconsultores.utils.appvalidation.InterpretadorDeSerial;
import java.text.SimpleDateFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Javier
 */
@Service
public class ActualizacionServiceImpl implements ActualizacionService {

    private static final Logger log = LoggerFactory.getLogger(ActualizacionServiceImpl.class);
    private static final String PROGRAM_UPDATE_COMMAND_7 = "war_updater.jar";
    private static final String FTP_PROPERTIES_HOST = "#{ftpProperties['host']}";
    private static final String FTP_PROPERTIES_USER = "#{ftpProperties['user']}";
    private static final String FTP_PROPERTIES_PASSWORD = "#{ftpProperties['password']}";
    private static final String FTP_PROPERTIES_PORT = "#{ftpProperties['port']}";
    private static final String FTP_PROPERTIES_HOST_FOLDER = "#{ftpProperties['hostFolder']}";
    private static final String FILE_PROPERTIES_NAME = "#{configProperties['file_index_name']}";
    private static final String FILE_PROPERTIES_FOTOS = "#{configProperties['external_file_images']}";
    private static final String FTP_PROPERTIES_HOST_FOLDER_IMAGE = "#{ftpProperties['hostImageFolder']}";
    private static final String ZIP_FILE_EXTENSION = ".zip";

    
    @Value(FTP_PROPERTIES_HOST_FOLDER_IMAGE)
	private String hostFolderImage;
    
    @Value(FILE_PROPERTIES_NAME)
	private String diferencias;
    
    @Value(FILE_PROPERTIES_FOTOS)
  	private String fotosUrl;

    @Value(FTP_PROPERTIES_HOST)
    private String host;

    @Value(FTP_PROPERTIES_USER)
    private String user;

    @Value(FTP_PROPERTIES_PASSWORD)
    private String password;

    @Value(FTP_PROPERTIES_PORT)
    private String port;

    @Value(FTP_PROPERTIES_HOST_FOLDER)
    private String hostFolder;

    @Autowired
    private FTPUploaderService ftpUploaderService;

    @Autowired
    private CFSUpdateService cfsUpdateService;

    @Autowired
    private DumpService dumpService;

    @Autowired(required = true)
    private UsuarioService usuarioService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileSystemLocationService fileSystemLocationService;
    
	@Autowired(required = true)
	private FileProcessService fileCodificationService;

    @Autowired
    private TaskSchedulerService taskSchedulerService;


    @Override
    public UpdateType checkUpdates() throws Exception {

        log.info("checkea updates");
        UpdateType updateType = UpdateType.NONE;

        String systemUpdate = ftpUpdateChecker(LastUpdatedVersionType.PROGRAM_UPDATE_FILES);
        //si existe fecha de actualizacion del sistema
        if (StringUtils.isNotEmpty(systemUpdate)) {
            //ultima actualizcion
            long updateTime = cfsUpdateService.findLastUpdate(UpdateType.SYSTEM);
            //si no hay actualizacion entonces marca como actualizcion de sistema y db
            if (updateTime == 0) {
                updateType = UpdateType.DB_SYS;
                //si la fecha de actualizacion del sistema es inferior a la del ftp
            } else if (Long.valueOf(systemUpdate.split(",")[0]) > updateTime) {
                updateType = UpdateType.DB_SYS;

            }
        }

        //si no existe actualizacion del sistema entra
        if (UpdateType.NONE.equals(updateType)) {
            String dbUpdate = ftpUpdateChecker(LastUpdatedVersionType.DB_UPDATE_FILES);
            if (StringUtils.isNotEmpty(dbUpdate)) {
                long updateTime = cfsUpdateService.findLastUpdate(UpdateType.DATABASE);
                if (updateTime == 0) {
                    updateType = UpdateType.DATABASE;
                } else if (Long.valueOf(dbUpdate) > updateTime || Long.valueOf(dbUpdate) < updateTime) {
                    updateType = UpdateType.DATABASE;

                }

            }
        }

        String imgUpdate = ftpUpdateChecker(LastUpdatedVersionType.IMAGES_UPDATE_FILES);

        if (StringUtils.isNotEmpty(imgUpdate)) {
            long updateTime = cfsUpdateService.findLastUpdate(UpdateType.IMAGES);
            if (updateTime == 0) {
                log.info("Entro == 0 ",imgUpdate);
                updateType = imgUpdateTypeChecker(updateType);
            } else if (Long.parseLong(imgUpdate) > updateTime || Long.parseLong(imgUpdate) < updateTime) {
                log.info("Entro >< ",imgUpdate);
                updateType = imgUpdateTypeChecker(updateType);

            }
        }

        log.info("updates " + updateType.name());
        return updateType;
    }

    private UpdateType imgUpdateTypeChecker(UpdateType updateType) {
        UpdateType result = UpdateType.NONE;
        switch (updateType) {
            case NONE:
                result = UpdateType.IMAGES;
                break;
            case DATABASE:
                result = UpdateType.DB_IMG;
                break;
            case DB_SYS:
                result = UpdateType.ALL;
                break;
        }
        return result;
    }

    private String ftpUpdateChecker(LastUpdatedVersionType type) throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        List<String> result = ftpUploaderService
                .retrieveFileContent(output,
                        hostFolder, host, user, password, portChecker(), type.getLastUpdateName());
        return (result != null && !result.isEmpty()) ? result.get(0) : null;

    }

    private int portChecker() {
        int portNumber = 21;

        if (!StringUtils.isEmpty(port)) {

            try {
                portNumber = Integer.parseInt(port);
            } catch (NumberFormatException e) {
            }

        }
        return portNumber;
    }

    @Override
    public int performUpdates(UpdateType type, String userName) throws Exception {
        Users securityUser = null;
        if (userName != null) {
            securityUser = usuarioService.getUsuario(userName);

        }
        int timeToWaitMin = 0;

        switch (type) {
        case ALL:
            systemUpdate(securityUser);
            dbUpdate(securityUser);
            imagesUpdate(securityUser);
            timeToWaitMin = updateApp();
            break;
        case DATABASE:
            dbUpdate(securityUser);
            break;
        case DB_IMG:
            dbUpdate(securityUser);
            break;
        case DB_SYS:
            systemUpdate(securityUser);
            dbUpdate(securityUser);
            timeToWaitMin = updateApp();
            break;
        case IMAGES:
        	imagesUpdate(securityUser);
            break;

        }
        return timeToWaitMin;
    }

    private void retrieveFiles(ByteArrayOutputStream output,LastUpdatedVersionType type, boolean binary) throws Exception {

       
        ftpUploaderService.retrieveFile(output, hostFolder, host, user, password, portChecker(), type.getUpdateDataFileName(), binary);
    }

    private void dbUpdate(Users securityUser) throws Exception {
        String dbUpdate = ftpUpdateChecker(LastUpdatedVersionType.DB_UPDATE_FILES);
        log.info("db update " + dbUpdate);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        retrieveFiles(output,LastUpdatedVersionType.DB_UPDATE_FILES, false);
        Long lastSavedTimeStamp = Long.parseLong(dbUpdate);
        securityUser.setUdateTimeStamp(lastSavedTimeStamp);
        fileService.createDirectory(fileSystemLocationService.getWarFileSystemLocation()+"\\update");
        fileService.saveByteArrayOutPutStreamToFile(output,fileSystemLocationService.getWarFileSystemLocation()+"\\update\\"+LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName());
        output.flush();
        output.close();
        usuarioService.modificarUsuario(securityUser);
        cfsUpdateService.update(securityUser, lastSavedTimeStamp, UpdateType.DATABASE);        
        dumpService.realizarVolcadoSql();
        log.info("db update completo");
    }

    private void systemUpdate(Users securityUser) throws Exception {

        String systemUpdate = ftpUpdateChecker(LastUpdatedVersionType.PROGRAM_UPDATE_FILES);
        log.info("sys update " + systemUpdate);
        byte[] war = retrieveByteFile(LastUpdatedVersionType.PROGRAM_UPDATE_FILES);
        fileService.saveByteFile(war, fileSystemLocationService.getWarFileSystemLocation() + File.separatorChar + LastUpdatedVersionType.PROGRAM_UPDATE_FILES.getUpdateDataFileName());
        cfsUpdateService.update(securityUser, Long.parseLong(systemUpdate.split(",")[0]), UpdateType.SYSTEM);

    }

    private void imagesUpdate(Users securityUser) throws Exception {
    	String url = fileSystemLocationService.getWarFileSystemLocation() + File.separatorChar+fotosUrl;
    	log.info("comenzando la actualizacion de fotos");
    	List<String> filesToDownload = getFilesToDownload(url);
		processFiles(filesToDownload,url);
		String imgUpdate = ftpUpdateChecker(LastUpdatedVersionType.IMAGES_UPDATE_FILES);
		long updateTime = Long.valueOf(imgUpdate);
		cfsUpdateService.update(securityUser, updateTime, UpdateType.IMAGES);
		log.info("imagenes actualizadas");	
    }

	private void processFiles(List<String> filesToDownload,String url) throws Exception {
		
		if(!CollectionUtils.isEmpty(filesToDownload)){
			try{
				ftpUploaderService.retrieveFileFromFtpAndSaveLocal( hostFolderImage, host, user, password, portChecker(),filesToDownload,url);	
			}catch(Exception e){
				// si el proceso falla se intenta una vez mas bajar las fotos restantes
				log.info("se produjo un error al conectarse con ftp, reintentando...");
				filesToDownload = getFilesToDownload(url);
				ftpUploaderService.retrieveFileFromFtpAndSaveLocal( hostFolderImage, host, user, password, portChecker(),filesToDownload,url);
				log.info("descarga de fotos completa...");
			}
			
		
		}
		
	}
	
	private List<String> getFilesToDownload(String url) throws Exception {
	   	File file = new File(url);
       	file.mkdir();
       	File ds = new File(url);
    	fileCodificationService.createCodificationFile(ds.list(), url);
    	List<String> ftpContentIndex = fileCodificationService.getContentFtpIndexFile(hostFolder,host, user, password,port,diferencias);
		List<String> localContentIndex = fileCodificationService.getContentLocalIndexFile(url+diferencias);
		List<String> filesToDownload = fileCodificationService.getNameOfFilesToProcess(localContentIndex,ftpContentIndex);
		return filesToDownload;
	}
	
	 private int updateApp() throws Exception {
	    String warFolder = fileSystemLocationService.getWarFileSystemLocation();
	    String webAppsFolder = fileSystemLocationService.getTomcatWebappsFileSystemLocation();
	    log.info("Correr el update del sistema backup :" + warFolder + " webapps :" + webAppsFolder);
	    taskSchedulerService.deleteExistentTask();
	    return taskSchedulerService.configureNewTask(warFolder + File.separatorChar + PROGRAM_UPDATE_COMMAND_7, warFolder, webAppsFolder);
    }

    private byte[] retrieveByteFile(LastUpdatedVersionType type) throws Exception {
       ByteArrayOutputStream output = new ByteArrayOutputStream();
       return ftpUploaderService.retrieveByteFile(output, hostFolder, host, user, password, portChecker(), type.getUpdateDataFileName());
    }	

    @Override
    public int performManualUpdates(MultipartFile mf) {
          String fileName ="update.zip";
          int result = -1;
        try {

            if (mf.getOriginalFilename().contains(ZIP_FILE_EXTENSION)) {

                dumpService.createManualUpdateFile(mf.getInputStream(),fileSystemLocationService.getWarFileSystemLocation(), fileName);
                
                dumpService.realizarVolcadoSql();
                
                fileService.createDirectory(fileSystemLocationService.getWarFileSystemLocation()+File.separatorChar+"imagenes");
                fileService.fileCopy(fileSystemLocationService.getWarFileSystemLocation()+File.separatorChar+"update"+File.separatorChar+"img", fileSystemLocationService.getWarFileSystemLocation()+File.separatorChar+"imagenes");
                String updateTime=fileService.convertFileToString(fileSystemLocationService.getWarFileSystemLocation()+File.separatorChar+"update"+File.separatorChar+"update.version");

                
                long updateTimeStamp = Long.parseLong(updateTime);
                Users usuario = usuarioService.obtenerUsuario();

                usuario.setUdateTimeStamp(updateTimeStamp);
                usuarioService.modificarUsuario(usuario);
                cfsUpdateService.update(usuario, updateTimeStamp, UpdateType.DATABASE);
                cfsUpdateService.update(usuario, updateTimeStamp, UpdateType.IMAGES);
                cfsUpdateService.update(usuario, updateTimeStamp, UpdateType.SYSTEM);
                return updateApp();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
