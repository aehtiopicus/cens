/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war_updater;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

/**
 *
 * @author Javier
 */
public class CopyService {

   
    public byte[] convertFileToByteArray(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public void copyFile(String filePath, byte[] infoToBeBackedUP) throws Exception {
        Path path = Paths.get(filePath);
        Files.write(path, infoToBeBackedUP);        
    }
}
