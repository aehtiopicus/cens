/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.com.midasconsultores.catalogodefiltros.repository;

import ar.com.midasconsultores.catalogodefiltros.domain.SystemUpdate;
import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Javier
 */
public interface CFSUpdateRepository extends JpaRepository<SystemUpdate, Long>{
    
    @Query(value = "SELECT coalesce(MAX(su.udateTimeStamp), '0') FROM SystemUpdate su WHERE su.updateType = :updateType")
    public Long findLatestUpdate(@Param(value="updateType") UpdateType updateType);

    @Query(value="SELECT su FROM SystemUpdate su WHERE su.updateType = :updateType")
    public SystemUpdate findOneByUpdateType(@Param("updateType")UpdateType updateType);
}
