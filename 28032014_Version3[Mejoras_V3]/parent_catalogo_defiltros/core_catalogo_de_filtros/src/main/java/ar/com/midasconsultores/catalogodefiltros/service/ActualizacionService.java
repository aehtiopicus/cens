/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.com.midasconsultores.catalogodefiltros.service;

import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Javier
 */
public interface ActualizacionService {
    
    public UpdateType checkUpdates() throws Exception;
    
    public int performUpdates(UpdateType type, String userName)throws Exception;

    public int performManualUpdates(MultipartFile mf);
}
