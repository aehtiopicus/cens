/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import ar.com.midasconsultores.catalogodefiltros.utils.LastUpdatedVersionType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Javier
 */
@Service
public class DumpServiceImpl implements DumpService {

    private static final Logger log = LoggerFactory.getLogger(DumpServiceImpl.class);

    private static final String CONFIG_PROPERTIES_ZIP_MAIN_DIR = "#{configProperties['update_package']}";

    private static final String CONFIG_PROPERTIES_ZIP_FILE = "#{configProperties['update_package_zip']}";

    private static final String CONFIG_PROPERTIES_ZIP_DIR = "#{configProperties['update_package_dir']}";

    private static final String CONFIG_PROPERTIES_ZIP_IMG_DIR = "#{configProperties['update_package_dir_img']}";

    private static final String CONFIG_PROPERTIES_IMG_PATH = "#{configProperties['external_file_path']}";

    private static final String CONFIG_PROPERTIES_VERSION_FILE = "#{configProperties['update_package_version_file']}";

    @Autowired
    private SqlDumpService sqlDumpService;

    @Autowired
    private FileService fileService;

    @Value(CONFIG_PROPERTIES_ZIP_MAIN_DIR)
    private String zipMainDir;

    @Value(CONFIG_PROPERTIES_ZIP_FILE)
    private String zipFile;

    @Value(CONFIG_PROPERTIES_ZIP_DIR)
    private String zipDir;

    @Value(CONFIG_PROPERTIES_ZIP_IMG_DIR)
    private String zipImgDir;

    @Value(CONFIG_PROPERTIES_IMG_PATH)
    private String imgDir;

    @Value(CONFIG_PROPERTIES_VERSION_FILE)
    private String lastUpdatedSystem;

//    @Override
//    public byte[] codificarSQLDump() throws Exception {
//        return sqlDumpService.codificarSQLDump();
//    }
    @Override
    public void obtenerSqlDump(OutputStream os)throws Exception{
        sqlDumpService.obtenerSqlDump(os);
    }

//    @Override
//    public String realizarVolcadoDB(String path) throws Exception {
//        return sqlDumpService.realizarVolcadoDB(path);
//    }
    
     @Override
    public void realizarVolcadoSql() throws Exception{
        sqlDumpService.realizarVolcadoSqlDB();
    }

    @Override
    public void getAllUpdates(OutputStream baos) throws Exception {
        //obtener dump,
        //obtener war
        //obetener imagenes
        //meter todo en un directorio
        //comprimir el directorio
        //obtener los bytes y voila

        String zipMainDirectory = System.getProperty("catalina.base") + File.separatorChar + zipMainDir;
        String zipDirectory = System.getProperty("catalina.base") + File.separatorChar + zipDir;
        String zipImgDirectory = System.getProperty("catalina.base") + File.separatorChar + zipImgDir;

        String warFileLocation = System.getProperty("catalina.base") + File.separatorChar + "webapps" + File.separatorChar + LastUpdatedVersionType.PROGRAM_UPDATE_FILES.getUpdateDataFileName();
        log.info("zipMainDirectory " + zipMainDirectory);
        log.info("zipDirectory " + zipDirectory);
        log.info("zipImgDirectory " + zipImgDirectory);
        log.info("warFileLocation " + warFileLocation);

        fileService.createDirectory(zipMainDirectory);
        fileService.createDirectory(zipDirectory);
        fileService.createDirectory(zipImgDirectory);

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//        fileService.saveByteFile(sqlDumpService.codificarSQLDump(), zipDirectory + File.separatorChar + LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName());
        sqlDumpService.obtenerSqlDump(baos2);
        fileService.saveByteArrayOutPutStreamToFile(baos2, zipDirectory + File.separatorChar + LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName());
        fileService.saveByteFile(fileService.convertFileToByteArray(warFileLocation), zipDirectory + File.separatorChar + LastUpdatedVersionType.PROGRAM_UPDATE_FILES.getUpdateDataFileName());
        fileService.fileCopy(imgDir, zipImgDirectory);
        fileService.saveFile(Long.toString(Calendar.getInstance().getTimeInMillis()), zipDirectory + File.separatorChar + lastUpdatedSystem);

        fileService.zipFolder(zipDirectory, zipMainDirectory + File.separatorChar + zipFile);

//        return fileService.convertFileToByteArray(zipMainDirectory + File.separatorChar + zipFile);
        fileService.copyFileToOutputStream(zipMainDirectory + File.separatorChar + zipFile, baos);

    }

    @Override
    public void createManualUpdateFile(InputStream is, String workPath, String zipName) throws Exception {
        try {
            fileService.cleanWarDirectory(workPath);
            fileService.saveByteFile(is, workPath + File.separatorChar + zipName);
            fileService.unZip(workPath + File.separatorChar + zipName);
            String pathDir = workPath + File.separatorChar + "update" + File.separatorChar;
            File[] fileList = fileService.getFileList(pathDir);
            if (fileList == null || fileList.length != 4) {
                log.error("no tiene la longitud requerida "+fileList.length);
                fileService.cleanWarDirectory(workPath);
                throw new Exception("El archivo no es correcto");
            } else {
                int count = 0;
                for (File f : fileList) {
                    String name = f.getName();
                    if (name.equals("img") || name.equals(LastUpdatedVersionType.PROGRAM_UPDATE_FILES.getUpdateDataFileName()) || name.equals(LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName()) || name.equals(lastUpdatedSystem)) {
                        log.info(name);
                        count++;
                    }
                }
                if (count != 4) {
                    fileService.cleanWarDirectory(workPath);
                    log.error("No estan los archivos que se necesitan");
                    throw new Exception("El archivo no es correcto");
                }
            }
            fileService.saveByteFile(fileService.convertFileToByteArray(pathDir + LastUpdatedVersionType.PROGRAM_UPDATE_FILES.getUpdateDataFileName()), workPath + File.separatorChar + LastUpdatedVersionType.PROGRAM_UPDATE_FILES.getUpdateDataFileName());
        } finally {
            is.close();
        }
    }
}
