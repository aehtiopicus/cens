/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.com.midasconsultores.catalogodefiltros.service;

import ar.com.midasconsultores.catalogodefiltros.domain.SystemUpdate;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.repository.CFSUpdateRepository;
import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Javier
 */
@Service
public class CFSUpdateServiceImpl implements CFSUpdateService{

    @Autowired
    private CFSUpdateRepository repository;
    
    @Override
    public void update(Users user, long updateTime, UpdateType updateType) {
        SystemUpdate su = repository.findOneByUpdateType(updateType);
        if(su==null){
            su = new SystemUpdate();
        }
        su.setUdateTimeStamp(updateTime);
        su.setUpdateType(updateType);
        su.setUser(user);
        repository.save(su);
    }
    
    @Override
    public long findLastUpdate(UpdateType updateType){
        return repository.findLatestUpdate(updateType);
    }
    
}
