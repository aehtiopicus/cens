/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war_updater;

/**
 * This class resolves the issue of creating threads with parameters 
 * @author aehtiopicus
 */
public class SimonSolivallasThread extends Thread {

    private final Object[] args;

    /**
     * add the list of parameters that are going to be used during {@link Thread} run method
     * @param args 
     */
    public SimonSolivallasThread(Object... args) {
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

}
