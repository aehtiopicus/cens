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
public enum LastUpdatedVersionType {

    DB_UPDATE_FILES("last_update_2.version", "update.cfs"),
    PROGRAM_UPDATE_FILES("last_update_data.version", "web_catalogo_de_filtros.war"),
    IMAGES_UPDATE_FILES("last_update_fotos.txt", "indice.txt");
    private String lastUpdateName;
    private String updateDataFileName;

    private LastUpdatedVersionType(String lastUpdateFileName, String updateDataFileName) {
        this.lastUpdateName = lastUpdateFileName;
        this.updateDataFileName = updateDataFileName;
    }

    public String getLastUpdateName() {
        return lastUpdateName;
    }

    public String getUpdateDataFileName() {
        return updateDataFileName;
    }
    
    
    
}
