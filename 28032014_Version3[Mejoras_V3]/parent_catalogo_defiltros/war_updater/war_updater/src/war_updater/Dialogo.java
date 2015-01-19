/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package war_updater;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Javier
 */
public class Dialogo extends JDialog {

    public void initDialogSubmit(Action submit, Dimension dimension) {
        initDialog(submit, null, dimension);
    }

    public void initDialogCancel(Action cancel, Dimension dimension) {
        initDialog(null, cancel, dimension);
    }

    public void initDialog(Action submit, Action cancel, Dimension dimension) {
        setSize(dimension);
        setLocationRelativeTo(null);

        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();
        if (submit != null) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
            actionMap.put("submit", submit);
        }
        if (cancel != null) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
            actionMap.put("cancel", cancel);
        }



    }
    public void initDialog(Dimension dimension) {
        setSize(dimension);
        setLocationRelativeTo(null);        

    }
}
