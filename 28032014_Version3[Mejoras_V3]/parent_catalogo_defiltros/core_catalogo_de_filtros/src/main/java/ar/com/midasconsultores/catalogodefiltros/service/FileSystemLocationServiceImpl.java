/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.File;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Javier
 */
@Service
public class FileSystemLocationServiceImpl implements FileSystemLocationService {

    private final String OS_COMMAND = "wmic.exe";
    private final String SERVICE_PARAMETER = "service";
    private final String TOMCAT_START_SERVICE = "cfsStart";
    private final String SERVICE_PARAMETER_2 = "get";
    private final String SERVICE_PARAMETER_3 = "PathName";
    private final String CARET_RETURN = "\r\n";
    private final String PGSQL = "\\pgsql";
    private final String TOMCAT_WEBAPPS = "\\tomcat\\webapps";
    private final String TOMCAT_WEBAPPS_ONLY ="\\webapps";
    private final String WAR_LOCATION = "\\war";
    private final String CFS_HOME_WEBAPPS = "\\CFS\\catalogo de filtros" + TOMCAT_WEBAPPS;
    private final String CFS_HOME_WAR = "\\CFS\\catalogo de filtros" + WAR_LOCATION;
    private final String CATALINA_BASE = "catalina.base";

    private static final Logger log = LoggerFactory.getLogger(FileSystemLocationServiceImpl.class);

    @Autowired
    private DBLocationService dbLocationService;

    @Override
    public String getDBFileSystemLocation() throws Exception {
        log.debug("Buscando el directorio de la BD");
        return dbLocationService.obtenerRutaInstalacion();
    }

    @Override
    public String getTomcatWebappsFileSystemLocation() throws Exception {
        log.debug("Buscando el dir de webapps");
        String result = null;
        result = getTomcatWebappsFileSystemLocationFromProcess();
        if (!checkDirectoryExistence(result)) {
            log.info("No se encontro el directorio de webapps en el proceso");
            result = getTomcatWebappsFileSystemLocationFromDBquery();
            if (!checkDirectoryExistence(result)) {
                log.info("No se encontro el directorio de webapps en la base");
                result = getTomcatWebappsFileSystemLocationHardCoded();
                if (!checkDirectoryExistence(result)) {
                    log.error("El directorio de webapps no puede ser encontrado");
                    throw new Exception("Directorio no encontrado");
                }
            }
        }
        return result;
    }

    @Override
    public String getWarFileSystemLocation() throws Exception {

        log.debug("Buscando el dir de WAR");
        String result = null;
        result = getWarFileSystemLocationFromProcess();
        if (!checkDirectoryExistence(result)) {            
            log.info("No se encontro el directorio WAR en el proceso");
            result = getWarFileSystemLocationFromDBquery();
            if (!checkDirectoryExistence(result)) {
                log.info("No se encontro el directorio WAR en la base");
                result = getWarFileSystemLocationHardCoded();
                if (!checkDirectoryExistence(result)) {
                    log.error("El directorio WAR no puede ser encontrado");
                    if(!new File(result).mkdirs()){
                         throw new Exception("Directorio WAR no encontrado");
                    }
                }
            }
        }
        return result;
    }

    private boolean checkDirectoryExistence(String path) {
        boolean exist = false;
        if (path != null) {
            File f = new File(path);
            if (f.exists() && f.isDirectory()) {
                exist = true;
            }
        }
        return exist;
    }

    private String getTomcatWebappsFileSystemLocationFromProcess() {

        String result = null;
        try {
            result = getInstallationPath();
            if (result != null) {
                result = result + TOMCAT_WEBAPPS;
            }

        } catch (Exception e) {
        }
        return result;
    }
    
     private String getWarFileSystemLocationFromProcess() {

        String result = null;
        try {
            result = getInstallationPath();
            if (result != null) {
                result = result + WAR_LOCATION;
            }

        } catch (Exception e) {
        }
        return result;
    }

    private String getInstallationPath() throws Exception {
        String result = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(OS_COMMAND, SERVICE_PARAMETER, TOMCAT_START_SERVICE, SERVICE_PARAMETER_2, SERVICE_PARAMETER_3);
            Process proc = pb.start();
            Thread.sleep(600);
            StringWriter sw = new StringWriter();
            IOUtils.copy(proc.getInputStream(), sw);
            String array[] = sw.toString().split(CARET_RETURN);
            for (String single : array) {
                if (single.toLowerCase().contains(PGSQL)) {
                    result= single.substring(0, single.indexOf(PGSQL))+"\"";
                    return result.replace("\\", "/");
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    private String getTomcatWebappsFileSystemLocationFromDBquery() {
        String result = null;
        try {
            String ruta = getFileSystemLocationFromDBquery();
            if(ruta!=null){
                result = ruta + TOMCAT_WEBAPPS;
            }
        } catch (Exception e) {
        }
        return result;
    }
    
    private String getWarFileSystemLocationFromDBquery() {
        String result = null;
        try {
            String ruta = getFileSystemLocationFromDBquery();
            if(ruta!=null){
                result = ruta + WAR_LOCATION;
            }
        } catch (Exception e) {
        }
        return result;
    }
    private String getFileSystemLocationFromDBquery() {
        String result = null;
        try {
            String ruta = dbLocationService.obtenerRutaInstalacion();
            result = ruta.substring(0, ruta.lastIndexOf(PGSQL));            
        } catch (Exception e) {
        }
        return result;
    }

    private String getTomcatWebappsFileSystemLocationHardCoded() {        
        return System.getProperty(CATALINA_BASE) + TOMCAT_WEBAPPS_ONLY;
    }
    
    private String getWarFileSystemLocationHardCoded() {
        String tomcatBase=System.getProperty(CATALINA_BASE);
        return tomcatBase.substring(0, tomcatBase.lastIndexOf(File.separatorChar))+WAR_LOCATION;        
    }

}
