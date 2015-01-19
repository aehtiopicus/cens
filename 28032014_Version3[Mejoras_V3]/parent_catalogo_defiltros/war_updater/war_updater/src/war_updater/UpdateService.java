/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war_updater;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 *
 * @author Javier
 */
public class UpdateService {

    private static final String DEPLOYED_DIR = File.separatorChar + "web_catalogo_de_filtros";
    private static final String WORK_DIR = "work";
    private static final String WAR_NAME = File.separatorChar + "web_catalogo_de_filtros.war";
    private static final String BACK_UP_NAME = "." + new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date()) + ".old";

    private final CopyService copy = new CopyService();
    private final FileService fileService = new FileService();
    private final OSProcessService process = new OSProcessService();

    public void performUpdate(String pathTo, String pathFrom) throws Exception {
        System.out.println(pathTo);
        System.out.println(pathFrom);
        process.setPath(pathTo);

//        WarUpdater.jdLoading.setMensaje("Deteniendo Servidor");
        process.stopTomcatService();
        Thread.sleep(15000);
//        WarUpdater.jdLoading.setMensaje("Actualizando");
        fileService.deleteDirectory(pathFrom + DEPLOYED_DIR);
        fileService.deleteDirectory(pathFrom.substring(0, pathFrom.toLowerCase().lastIndexOf("webapps")) + WORK_DIR);

        try {
            copy.copyFile(pathTo + WAR_NAME + BACK_UP_NAME, copy.convertFileToByteArray(pathFrom + WAR_NAME));
        } catch (Exception e) {
            System.out.println("Error");
            throw e;
        }
        Thread.sleep(500);
        try {
            copy.copyFile(pathFrom + WAR_NAME, copy.convertFileToByteArray(pathTo + WAR_NAME));
        } catch (Exception e) {
            throw new Exception(pathFrom + WAR_NAME + "   " + pathTo + WAR_NAME);
        }
//        WarUpdater.jdLoading.setMensaje("Iniciando ");
        process.startTomcatService();
        Thread.sleep(15000);
        process.killChrome();
        process.cleanChrome();
//        process.startChrome(chrome);
    }

    void cleanAll(String pathTo) throws Exception {
        fileService.cleanAll(pathTo);
    }
}
