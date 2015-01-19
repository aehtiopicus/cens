/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.domain;

import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;



/**
 *
 *
 */
@Entity
@Table(name = "cfs_system_update")
public class SystemUpdate implements Serializable {
         
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "cfs_system_update_generator", sequenceName="cfs_system_update_sequence", allocationSize=1)
//    @TableGenerator(name = "cfs_system_update_generator", table = "generic_generator", valueColumnName = "sequence_value", pkColumnValue = "cfs_system_update_gen",  initialValue = 10000, allocationSize = 100)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cfs_system_update_generator")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "update_type", nullable = false)
    private UpdateType updateType;
    
    @Column(name = "udate_time_stamp", nullable = true)
    private long udateTimeStamp;
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(UpdateType updateType) {
        this.updateType = updateType;
    }

    public long getUdateTimeStamp() {
        return udateTimeStamp;
    }

    public void setUdateTimeStamp(long udateTimeStamp) {
        this.udateTimeStamp = udateTimeStamp;
    }
    
    
    
    
    
}
