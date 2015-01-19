/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

/**
 *
 * @author Javier
 */
public interface FileSystemLocationService {

    public String getDBFileSystemLocation() throws Exception;

    public String getTomcatWebappsFileSystemLocation() throws Exception;
    
     public String getWarFileSystemLocation() throws Exception ;
}
