/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.com.midasconsultores.catalogodefiltros.service;

import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;


/**
 *
 * @author Javier
 */
public interface CFSUpdateService {
    
    public void update(Users user, long updateTime, UpdateType updateType);
    
    public long findLastUpdate(UpdateType updateType);
}
