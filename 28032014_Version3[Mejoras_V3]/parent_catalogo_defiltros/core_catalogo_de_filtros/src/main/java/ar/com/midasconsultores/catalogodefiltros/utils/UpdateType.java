/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.utils;

/**
 *
 * @author Javier
 */
public enum UpdateType {

    NONE(0),
    DATABASE(1),
    SYSTEM(2),
    IMAGES(3),
    ALL(4),    
    DB_SYS(5),
    DB_IMG(6);
    

    private final int updateType;

    private UpdateType(int updateType) {
        this.updateType = updateType;
    }

    public static UpdateType getUpdateType(int value) {

        for (UpdateType cut : values()) {
            if (cut.getUpdateType() == value) {
                return cut;
            }
        }
        return null;
    }

    public int getUpdateType() {
        return updateType;
    }

}
