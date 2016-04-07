/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author aehtiopicus
 */
@Service
public class TaskSchedulerServiceImpl implements TaskSchedulerService {

    private static final String TASK_NAME = "\"cfsCopier\"";
    private static final String COMMAND_NAME = "schtasks";
    private static final String DELETE_COMMAND_1 = "/Delete";
    private static final String DELETE_COMMAND_2 = "/TN";
    private static final String DELETE_COMMAND_3 = "/F";
    private static final String CREATE_COMMAND_1 = "/Create";
    private static final String CREATE_COMMAND_2 = "/SC";
    private static final String CREATE_COMMAND_3 = "ONCE";
    private static final String CREATE_COMMAND_4 = "/TN";
    private static final String CREATE_COMMAND_5 = "/TR";
    private static final String CREATE_COMMAND_6 = "/ST";
    private static final String CREATE_COMMAND_7 = "/RU";
    private static final String CREATE_COMMAND_8 ="SYSTEM";
    private static String processTemplate = "\"java -jar '<copier_jar>' '<from_folder>' '<to_folder>'\"";
    private static final String COPIER_JAR = "<copier_jar>";
    private static final String FROM_FOLDER = "<from_folder>";
    private static final String TO_FOLDER = "<to_folder>";
    
    private static final String CONFIGURE_NEW_TASK ="config de nueva tarea";
    private static final String DELETE_NEW_TASK ="borrado de tarea";

    @Autowired
    private FileSystemLocationService fileSystemLocationService;

    /**
     *
     * @throws Exception
     */
    @Override
    public void deleteExistentTask() throws Exception {
        ProcessBuilder pb = new ProcessBuilder(COMMAND_NAME, DELETE_COMMAND_1, DELETE_COMMAND_2, TASK_NAME, DELETE_COMMAND_3);
        runProcess(pb,DELETE_NEW_TASK);
    }

    @Override
    public int configureNewTask(String copierPath, String fromPath, String toPath) throws Exception {

        if (fromPath.endsWith(File.pathSeparator)) {
            fromPath = fromPath.substring(0, fromPath.length() - 1);
        }

        if (toPath.endsWith(File.pathSeparator)) {
            toPath = toPath.substring(0, toPath.length() - 1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String task = processTemplate.replace(COPIER_JAR, copierPath).replace(FROM_FOLDER, fromPath).replace(TO_FOLDER, toPath);
        Calendar c = Calendar.getInstance();
        int timeToWait = 0;        
        if(c.get(Calendar.SECOND)>40){
            timeToWait = 2;
        }else{
            timeToWait = 1;
            
        }
        c.add(Calendar.MINUTE, timeToWait);
        ProcessBuilder pb = new ProcessBuilder(COMMAND_NAME, CREATE_COMMAND_1, CREATE_COMMAND_2, CREATE_COMMAND_3, CREATE_COMMAND_4, TASK_NAME, CREATE_COMMAND_5, task, CREATE_COMMAND_6, sdf.format(c.getTime()),CREATE_COMMAND_7,CREATE_COMMAND_8);
        runProcess(pb,CONFIGURE_NEW_TASK);
        // 1:20 min como mucho
        if(timeToWait==2){
            timeToWait = 75000;
        }else{
            timeToWait = 60000;
        }
            
        return timeToWait;
    }

    private void runProcess(ProcessBuilder pb,String process) throws Exception {
        BufferedReader br = null;
        FileWriter fw = null;
        try {
            Process p = pb.start();
            Thread.sleep(1000);
            br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine()).append("\r\n");
            }
            String result ="WEB_CATALOGO_DE_FILTROS >>> "+process+"\r\n "+ sb.toString();
            if (result != null && !result.isEmpty()) {
                fw = new FileWriter(new File(fileSystemLocationService.getWarFileSystemLocation() + File.separatorChar + "copier.log"),true);
                fw.write(result);
                fw.flush();

            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (fw != null) {
                fw.close();
            }
        }
    }
}
