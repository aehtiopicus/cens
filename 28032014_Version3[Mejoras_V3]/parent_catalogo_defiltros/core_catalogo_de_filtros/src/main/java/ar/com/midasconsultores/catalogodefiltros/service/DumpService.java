/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Javier
 */
public interface DumpService {

//    public byte[] codificarSQLDump() throws Exception;

//    public String realizarVolcadoDB(String path) throws Exception;

//    public byte[] getAllUpdates() throws Exception;
    
    public void getAllUpdates(OutputStream os) throws Exception;
    
    public void createManualUpdateFile(InputStream is,String workPath, String path)throws Exception;

    public void obtenerSqlDump(OutputStream os) throws Exception;
    
    public void realizarVolcadoSql() throws Exception;
}
