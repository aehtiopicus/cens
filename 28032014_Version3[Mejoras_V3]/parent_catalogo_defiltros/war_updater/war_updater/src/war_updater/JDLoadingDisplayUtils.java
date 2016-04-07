/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package war_updater;

import java.awt.Window;

/**
 *
 * @author Javier
 */
public class JDLoadingDisplayUtils {
    
    private static JDLoadingDialog currentDisplaying;
    public static void setDisableWaitingDialog(boolean enable){
        if(!enable){
            checkJdLoadingDisplaying();
        }
        if(currentDisplaying!=null){            
            currentDisplaying.setVisible(enable);
            if(enable){
                currentDisplaying = null;
            }
        }
    }
    private static void checkJdLoadingDisplaying(){
        Window[] windows = Window.getWindows();
            if (windows != null) { // don't rely on current implementation, which at least returns [0].
                for (Window w : windows) {
                    if (w.isShowing() && w instanceof JDLoadingDialog ) {
                           currentDisplaying = (JDLoadingDialog) w;                     
                    }
                }
            }
    }
}
