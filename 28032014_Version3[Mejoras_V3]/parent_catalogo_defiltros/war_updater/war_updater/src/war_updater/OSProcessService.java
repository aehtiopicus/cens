/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war_updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Javier
 */
public class OSProcessService {

    private static final String TASK_KILL = "taskkill.exe";
    private static final String TASK_KILL_PARAM_1 = "/f";
    private static final String TASK_KILL_PARAM_2 = "/IM";
    private static final String TASK_KILL_PARAM_3 = "chrome.exe";

    private static final String TOMCAT = "tomcat7";
    private static final String SERVICE_COMMAND = "sc.exe";
    private static final String STOP_COMMAND = "stop";
    private static final String START_COMMAND = "start";

    private static final String START_PARAMER_1 = "/B";
    private static final String START_PARAMER_2 = "chrome";
    private static final String START_PARAMER_3 = "http://localhost:8080/web_catalogo_de_filtros";

    private static final String CLEAN_CHROME_CACHE_PARAMETER_0 = "cmd";
    private static final String CLEAN_CHROME_CACHE_PARAMETER_1 = "/C";
    private static final String CLEAN_CHROME_CACHE_PARAMETER_2 = "del";
    private static final String CLEAN_CHROME_CACHE_PARAMETER_3 = "/q";
    private static final String CLEAN_CHROME_CACHE_PARAMETER_4 = "/s";
    private static final String CLEAN_CHROME_CACHE_PARAMETER_5 = "/f";

    private static final String CLEAN_CHROME_CACHE_PARAMETER_6 = "\"\"%USERPROFILE%\"";
    private static final String CLEAN_CHROME_CACHE_PARAMETER_7 = File.separatorChar + "AppData\\Local\\Google\\Chrome\\User Data\"";
    private static final String CLEAN_CHROME_CACHE_PARAMETER_8 = "rd";
    private String path;

    public void killChrome() throws Exception {

        ProcessBuilder builder = new ProcessBuilder(TASK_KILL, TASK_KILL_PARAM_1, TASK_KILL_PARAM_2, TASK_KILL_PARAM_3);
        runProcess(builder, "kill chrome");
    }

    public void cleanChrome() throws Exception {

        //cmd /C del /q /s /f "%USERPROFILE%"\AppData\Local\Google\Chrome\User Data
        ProcessBuilder builder = new ProcessBuilder(CLEAN_CHROME_CACHE_PARAMETER_0, CLEAN_CHROME_CACHE_PARAMETER_1,
                CLEAN_CHROME_CACHE_PARAMETER_2, CLEAN_CHROME_CACHE_PARAMETER_3,
                CLEAN_CHROME_CACHE_PARAMETER_4, CLEAN_CHROME_CACHE_PARAMETER_5,
                CLEAN_CHROME_CACHE_PARAMETER_6 + CLEAN_CHROME_CACHE_PARAMETER_7);
        runProcess(builder, "del chrome 1");
        //cmd /C rd /s /q "%USERPROFILE%"\AppData\Local\Google\Chrome\User Data
        builder = new ProcessBuilder(CLEAN_CHROME_CACHE_PARAMETER_0, CLEAN_CHROME_CACHE_PARAMETER_1,
                CLEAN_CHROME_CACHE_PARAMETER_8, CLEAN_CHROME_CACHE_PARAMETER_4,
                CLEAN_CHROME_CACHE_PARAMETER_3,
                CLEAN_CHROME_CACHE_PARAMETER_6 + CLEAN_CHROME_CACHE_PARAMETER_7);
        runProcess(builder, "rm chrome 1");
    }

    /**
     *
     * @param chrome
     * @throws Exception
     * @deprecated no se puede usar por que el usuario sys no es interactivo...
     */
    public void startChrome(String chrome) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(chrome);

        runProcess(builder, "start " + chrome);
    }

    void stopTomcatService() throws Exception {
        ProcessBuilder builder = new ProcessBuilder(SERVICE_COMMAND, STOP_COMMAND, TOMCAT);
        runProcess(builder, " stop tomcat");
    }

    void startTomcatService() throws Exception {
        ProcessBuilder builder = new ProcessBuilder(SERVICE_COMMAND, START_COMMAND, TOMCAT);
        runProcess(builder, " start tomcat");
    }

    private void runProcess(ProcessBuilder pb, String step) throws Exception {
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
            String result = sb.toString();
            if (result != null && !result.isEmpty()) {

                fw = new FileWriter(new File(path + File.separatorChar + "copier.log"), true);
                fw.write("ERROR en " + step + new java.util.Date() + ">>>" + "\r\n" + result + "\r\n");
                fw.flush();

            } else {
                fw = new FileWriter(new File(path + File.separatorChar + "copier.log"), true);
                fw.write("Correcto en" + step + " " + new java.util.Date() + "\r\n");
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

    void setPath(String pathFrom) {
        path = pathFrom;
    }
}
