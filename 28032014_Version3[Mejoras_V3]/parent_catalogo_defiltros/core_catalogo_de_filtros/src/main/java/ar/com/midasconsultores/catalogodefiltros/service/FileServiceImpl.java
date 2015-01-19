/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Javier
 */
@Service
public class FileServiceImpl implements FileService {

    private static final String PERSISTENCE_PROPERTIES_ENCRIPTATION_PASSWORD = "#{persistenceProperties['encription_password']}";

    private String zipPass = "cfs.zip";

    @Autowired
    private EncryptorDecriptorService encryptorDecriptorService;

    @Value(PERSISTENCE_PROPERTIES_ENCRIPTATION_PASSWORD)
    private String encriptationPassword;

    @Override
    public void saveFile(String stringToByteArray, String filePath) throws Exception {
        Path path = Paths.get(filePath);
        Files.write(path, stringToByteArray.getBytes());

    }

    @Override
    public void saveByteFile(byte[] stringToByteArray, String filePath) throws Exception {
        Path path = Paths.get(filePath);
        Files.write(path, stringToByteArray);

    }
     @Override
    public void saveByteArrayOutPutStreamToFile(ByteArrayOutputStream baos, String filePath) throws Exception {
        
        OutputStream outputStream = new FileOutputStream (filePath); 
        baos.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
        

    }
    
    @Override
    public void saveByteFile(InputStream is, String filePath) throws Exception {
        
        Path path = Paths.get(filePath);
        path.toFile().createNewFile();
        BufferedOutputStream bos =null;
        try{
            bos =new BufferedOutputStream(new FileOutputStream(path.toFile()));        
            IOUtils.copyLarge(is, bos);
        }finally{
            if(bos!=null){
                bos.flush();
                bos.close();
            }
        }

    }

    @Override
    public void fileCopy(String srcDirectory, String destDirectory) throws Exception {
//        FileUtils.copyDirectory(new File(srcDirectory), new File(destDirectory));
        for (File f : new File(srcDirectory).listFiles()) {
            FileUtils.copyFileToDirectory(f, new File(destDirectory));
        }
    }

    @Override
    public byte[] convertFileToByteArray(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    @Override
    public void copyFileToOutputStream(String filePath, OutputStream baos) throws Exception {
        Path path = Paths.get(filePath);
        FileInputStream fis = new FileInputStream(path.toFile());
        try {
            IOUtils.copyLarge(fis, baos);
        } finally {
            fis.close();
        }
    }

    @Override
    public String convertFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Charset.forName("windows-1252").decode(ByteBuffer.wrap(Files.readAllBytes(path))).toString();
//        return new String(Files.readAllBytes(path));
    }

    @Override
    public void createDirectory(String dirPath) throws Exception {
        File f = new File(dirPath);
        if (f.exists() && f.isDirectory()) {
            deleteDirectory(dirPath);
            f = new File(dirPath);
            //parece tonto pero no se si no funciona arriba...
            if(!f.exists()){
                 f.mkdirs();
            }
        } else {
            f.mkdirs();
        }
    }

    @Override
    public void deleteDirectory(String dirPath) throws Exception {
        // TODO Auto-generated method stub
        //declaring the path to delete
        Path path = Paths.get(dirPath);
//        System.out.println("Deleting recursivey : " + path);
        //browsing the file directory and delete recursively using java nio

        Files.walkFileTree(path, new FileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {
                // TODO Auto-generated method stub

//                System.out.println("deleting directory :" + dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                    BasicFileAttributes attrs) throws IOException {
                // TODO Auto-generated method stub
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {
                // TODO Auto-generated method stub
//                System.out.println("Deleting file: " + file);
                file.toFile().setWritable(true);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
                    throws IOException {
                // TODO Auto-generated method stub
//                System.out.println(exc.toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        try {
            fileWriter = new FileOutputStream(destZipFile);
            zip = new ZipOutputStream(fileWriter);

            addFolderToZip("", srcFolder, zip);
        } finally {
            if (zip != null && fileWriter != null) {
                zip.flush();

                zip.close();
                fileWriter.flush();
                fileWriter.close();
            }
        }
    }

    private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            try {
                zip.putNextEntry(new ZipEntry(path + File.separator + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
            } finally {
                in.close();
            }
        }
    }

    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + File.separator + fileName, zip);
            } else {
                addFileToZip(path + File.separator + folder.getName(), srcFolder + File.separator + fileName, zip);
            }
        }
    }

    @Override
    public void unZip(String path) throws Exception {
        String source = path;
        String destination = path.substring(0, path.lastIndexOf(File.separatorChar) + 1);
        net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(source);
        zipFile.extractAll(destination);

    }

    @Override
    public void cleanWarDirectory(String pathTo) throws Exception {
        File[] file = new File(pathTo).listFiles();
        for (File f : file) {
            if (f.isDirectory() && !f.getName().contains("imagenes")) {
                deleteDirectory(f.getAbsolutePath());
            } else if (f.isFile() && !f.getName().endsWith(".jar")) {
                f.delete();
            }

        }

    }

    @Override
    public File[] getFileList(String pathDir) {
        File f = new File(pathDir);
        if (!f.exists() || !f.isDirectory()) {
            return null;
        }
        return f.listFiles();
    }

}
