/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Javier
 */
public interface FileService {

    public void saveFile(String stringToByteArray, String filePath) throws Exception;

    public void saveByteFile(byte[] stringToByteArray, String filePath) throws Exception;

    public void createDirectory(String dirPath) throws Exception;

    public void deleteDirectory(String dirPath) throws Exception;

    public byte[] convertFileToByteArray(String filePath) throws IOException;

    public void fileCopy(String srcDirectory, String destDirectory) throws Exception;
    
    public void zipFolder(String srcFolder, String destZipFile) throws Exception;

    public void unZip(String path) throws Exception;
    
    public String convertFileToString(String filePath) throws IOException;

    public void cleanWarDirectory(String workPath)throws Exception ;

    public File[] getFileList(String pathDir);
    
    public void copyFileToOutputStream(String filePath, OutputStream baos) throws Exception; 
    
    public void saveByteFile(InputStream is, String filePath) throws Exception ;
    
    public void saveByteArrayOutPutStreamToFile(ByteArrayOutputStream baos, String filePath) throws Exception;
}
