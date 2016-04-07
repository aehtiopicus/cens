/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war_updater;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Javier
 */
public class WarUpdater {

//    public static final JDLoadingDialog jdLoading = new JDLoadingDialog(false, 0);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
                if (args != null && args.length == 2) {
            new SimonSolivallasThread(args[0], args[1]) {
                @Override
                public void run() {
                    try {
                        UpdateService updateService = new UpdateService();
                        updateService.performUpdate((String) getArgs()[0], (String) getArgs()[1]);
                        updateService.cleanAll((String) getArgs()[0]);
                    } catch (Exception e) {
                        try {

                            StringBuilder sb = new StringBuilder(e.getMessage()+"\r\n");                            
                            for (StackTraceElement ste : e.getStackTrace()) {
                                sb.append(ste.toString()).append("\r\n");
                            }
                            FileWriter fw = new FileWriter(new File((String) getArgs()[0] + File.separatorChar + "copier.log"), true);
                            fw.write("COMIENZA ERROR >>>>\r\n"+sb.toString()+"FIN ERROR \r\n");
                            fw.flush();                            
                        } catch (Exception ex) {

                        }
                    }
//                    jdLoading.setVisible(false);
//                    jdLoading.dispose();
                }
            }.start();
//            jdLoading.setVisible(true);

        }
//        System.exit(0);

    }

}
