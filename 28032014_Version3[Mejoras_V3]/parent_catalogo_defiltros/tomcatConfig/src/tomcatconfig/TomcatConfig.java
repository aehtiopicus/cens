/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tomcatconfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Javier
 */
public class TomcatConfig {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])throws Exception {        
        
        String javaPath=System.getProperty("java.home").substring(0,System.getProperty("java.home").lastIndexOf("Java")+4)+"\\jdk1.7.0_25";
        String catalinaHomePath=args[0]+"\\tomcat";
        StringBuilder sb = new StringBuilder();
        sb.append("set JAVA_HOME=").append(javaPath).append("@@@");
        sb.append("set CATALINA_HOME=").append(catalinaHomePath).append("@@@");
        sb.append("set PATH=%JRE_HOME%\\bin;%PATH%").append("@@@");
        sb.append("set PATH=%CATALINA_HOME%\\bin;%PATH%").append("@@@");
        
        BufferedWriter bw = new BufferedWriter(new FileWriter((new File(args[0]+"\\soft\\tomcatstartup.bat"))));
        String array[] = sb.toString().split("@@@");
        for(String s : array){
            bw.write(s);            
            bw.newLine();
        }
        bw.write("\""+catalinaHomePath+"\\bin\\service.bat"+"\""+ " install");    
            bw.newLine();
        
        
        bw.flush();
        bw.close();

    }
}
